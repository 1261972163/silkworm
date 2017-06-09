从2015年开始接触ES，先后实践过1.9.x -> 5.2.2中间各个版本，从最初只是用于运维内部分布式调用日志的分析，到现在支持业务非敏感数据的准实时检索与分析。踩过不少坑，现在总结一下。

Elasticsearch被称为免配置的搜索引擎，自己私下里玩一玩没有什么问题，但要在生产上应对千亿级数据，还是需要好好优化一下配置。以下内容都以5.2.2版本为准。

本文主要解决几个问题：

1. 集群管理。ES运行的硬件和操作系统环境、节点角色分离，冷热分离节点。
2. 索引管理。分片规划、数据规划、路由、主从分片、分片移动、段合并、集群重启、创建mapping、创建分词器、多索引创建、创建template。
3. 内存管理。认识ES的各种缓存、设置断路器、手动清理缓存。
4. 监控和安全。设置高低水平线、打印热线程、nginx代理。
5. 运维管理。集群迁移、自动化运维。
6. 搜索。

# 集群管理

硬件和操作系统是ES运行的环境，良好的运行配置能更好的发挥ES的功能：

* **内存**设置需要考虑2个部分：es和lucene。es依靠大量内存中数据结构提供快速操作。lucene使用底层操作系统缓存数据结构，lucene的segments是不可变的，十分适合于缓存，底层操作系统将热segments进行缓存，包括倒排索引和doc数据。lucene的性能依赖于这种交互。所以，如果将所有的内存都分配给es java heap了，lucene就没有用的了。对于lucene来说，**Lucene内存**有多少，它就能用多少内存。而**ES内存**配置不是越大越好，建议不能超过32GB。有兴趣的，可以去了解下神奇的[32G现象](https://www.elastic.co/guide/en/elasticsearch/guide/current/heap-sizing.html)。es官方建议使用内存的一半，剩下的给lucene使用。理想状态是单台机器64G内存。一般32G内存和16G内存机器也可以。

		-Xms32g
		-Xmx32g

* **jdk**。ES5需要运行在jdk8及以上版本。
* **GC收集器**。JDK6、JDK7、JDK8的默认GC类型是CMS，从使用场景上看，CMS不适合大内存的回收，造成stop-the-world的几率较高。从JDK7开始，推荐使用G1，可以减少stop-the-world在几率，但是CPU占有率高：

		-XX:+UseG1GC
		-XX:MaxGCPauseMillis=200

* **CPU**。要求多核心。一般的集群需要2核到8核的CPU。如果需要在多核和速度上进行取舍，选择多核。
* **硬盘**。硬盘对所有集群都很重要，尤其是索引频繁的集群。尽量使用SSD，在索引和查询上性能更好。使用SSD时，注意检查I/O调度，使用deadline或者noop。使用RAID0可以增加硬盘速度，不需要RAID镜像等特性。不要使用网络连接存储（NAS）。
* **网络**。分布式系统要求快速稳定的网络。尽量使用1GbE或者10GbE的网络。
* **集群**。建立集群时，我们既可以选择部署几个单机性能很强（几百G内存、几十个核的CPU）的大型机器，也可以选择在云端部署几千个小型虚拟机。一般来说，建议使用使用中大型机器。使用小型机，涉及到几个个小型机管理的问题，同时小型机上运行ES的开销更加明显。使用大型机，会导致资源利用的不平衡，比如内存用完了，但CPU却没有。同时，在大型机上运行管理多个node会增加集群逻辑复杂度。
* **文件描述数量和句柄数**。ES会大量使用文件描述和文件句柄，超过文件描述数量可能导致数据丢失，尽可能的增加可打开的文件描述数量。具体操作： 假设系统用户为elasticsearch。编辑 /etc/security/limits.conf ，添加：

	    elasticsearch  -  nofile  65536

* **禁止swapping**。大多数操作系统都会将文件系统缓存到内存，同时将未使用的应用内存swap掉，jvm堆swap到硬盘上，导致GC时间很长。有3中禁止swap的方法：

		1. 配置bootstrap.memory_lock。linux上执行 mlockall ，具体操作：编辑 $ES_HOME/config/elasticsearch.yml ，添加 bootstrap.memory_lock: true
		2. 完全禁止swap。具体操作：编辑 /etc/fstab，注释掉包含swap的行。
		3. 配置swappiness。减少kernel的swap倾向，紧急情况下仍然会使用swap。具体操作：编辑 /etc/sysctl.conf，设置 vm.swappiness = 1
	    
* **虚拟内存**。ES默认使用目录存放索引，系统默认的mmap计数比较小，可能导致内存溢出。

	    sysctl -w vm.max_map_count=262144

* **线程数**。假设系统用户为elasticsearch，编辑 /etc/security/limits.conf ，添加：

	    elasticsearch  -  nproc  20480

ES节点配置中有2个参数：node.master和node.data，可以组合出不同的角色。建议是对节点进行角色划分，让节点的功能单一化，只进行单个角色任务，避免不同角色功能间进行资源争夺，导致相互影响。

其中，最重要的是将**master角色**独立出来，master节点是集群的维护者，用来协调各种创建索引的请求、查询请求，将这些请求合理分发到相关的node服务器上，它本身不存储任何索引数据。如果master角色不可用，整个集群都将不可用。master的发现和高可用主要依靠下面两个参数：

	discovery.zen.ping.unicast.hosts，可以让节点知道集群中哪些节点是master节点
	discovery.zen.minimum_master_nodes，集群中master节点的最小数目（默认为1）

集群中master节点的最小数目很重要，如果小于该数目，即使数据节点没有宕机，整个集群仍然挂掉。如果设置这个最小数目不当，比如集群中共有2个master，设置最小数目为1，选举的时候，这个两个master可能被两个集群分别选为leader，这样一个集群分裂成两个集群，这就是**脑裂**。为了防止脑裂，建议取值：

	minimum_master_nodes = (master_eligible_nodes/2) + 1

鉴于master如此重要，同时master对资源要求不高，只需要保证基本运行，建议master节点单独部署，不要让它再承担其他的职责。

**data节点**是集群数据的持有者，用于存储索引数据，主要对文档进行增删改查操作，聚合操作等。

（1）因为涉及到文档读写，所以对cpu、内存、io要求较高，建议将data节点功能单一化，只用于数据存储和数据查询，一方面降低资源消耗率，另一方面避免对其他功能的影响。比如：不用开启http服务。将其中的配置参数这样设置：http.enabled: false，同时也不要安装head, bigdesk, marvel等监控 插件，这样保证data节点服务器只需处理创建/更新/删除/查询索引数据等操作。http功能可以在非数据节点服务器上开启，上述相关的监控插件也安装到这些服务器上。

（2）在优化的时候需要监控数据节点的状态，当资源不够的时候，需要在集群中添加新的节点。

（3）数据目录可以被多个节点共享，甚至可以属于不同的集群，为了防止多个节点共享相同的数据路径，可以在配置文件elasticsearch.yml中添加：

	node.max_local_storage_nodes: 1

还可以进一步的分离职责，将查询任务分离出来，将node.master和node.data都设置为false，这个节点就是**协调节点**或者**负载节点**。负载节点既不会被选作主节点，也不会存储任何索引数据。主要用于查询负载均衡，在查询的时候，通常会涉及到从多个node服务器上查询数据，并请求分发到多个指定的node服务器，并对各个node服务器返回的结果进行一个汇总处理，最终返回给客户端。

如果存储的数据具有明显的访问热度差别，可以进行**冷热分离**，将冷热数据分离到不同的节点上。热数据节点支持频繁的读写操作，建议使用更好的资源，存储空间不用太大，但SSD还是建议作为首选。定时将不是太热的数据从热节点移动到冷节点上进行存储，冷数据节点只接受读请求，读取存储历史数据，资源不用太好，存储空间够大就行。

硬盘是ES存储索引的地方，每个node的存储空间和使用量可能不一样，如果某个node被用完，仍在该node上建立索引将报错。ES的**高低水平线**可以最大化利用整个集群的存储空间：


	* 硬盘使用量高于低水平线时，ES不会在该node增加新分片。
	* 当磁盘使用量高于高水平线时，ES会将该node上的分片移出到其他node上。

相关配置：

    cluster.routing.allocation.disk.threshold_enabled: true //是否开启高低水平线，默认true
    cluster.routing.allocation.disk.watermark.low: 85%      //设置低水平线，默认85%
    cluster.routing.allocation.disk.watermark.high: 90%     //设置高水平线，默认90%
    cluster.info.update.interval: 30s                       //检查水平线的时间间隔，默认30s
    cluster.routing.allocation.disk.include_relocations: true //计算磁盘使用量时是否包含再分配的分片

如，设置低水平线不超过400gb，高水平线不超过500gb，1m刷新一次。

    curl -XPUT http://10.9.18.237:9200/_cluster/settings -d '{
      "transient": {
        "cluster.routing.allocation.disk.watermark.low": "400gb",
        "cluster.routing.allocation.disk.watermark.high": "500gb",
        "cluster.info.update.interval": "1m"
      }
    }'

# 索引管理

ES中一个index是由多个分片组成的，分片其实就是一个小的Lucene索引，分片数越多，意味着索引越快。但分片多会增加开销，如将单个查询请求分割成多个查询请求，发送到每个分片，然后将查询结果合并。为了最大化利用node，一般存在这样的关系：       

    节点数 = 单个Index分片数 * ( 单个Index备份数 + 1 )

分片数越多，需要的节点越多，因此不能设置太多分片。我们知道，一旦index建立，shard数目将不能再修改，这就需要在规划时谨慎考虑。

从技术角度看，**扩展shard**和**扩展index**并没有太大区别，两者的区别主要在操作上：

	* 扩展shard。一个index上有多个shard，需要使用routing方法才能操作指定的shard。
	* 扩展index。可以直接通过index名称选择所需要操作index上的数据，操作更方便。

一般来说，数据的划分有两个维度：时间和用户。一般来说，时间维度划分通过扩展index实现，用户维度划分通过**routing**实现。

ES5中，index分片的**primary数量和replica数量**都是不能配置文件中指定的，只能动态创建：

	curl -XPUT 'http://127.0.0.1:9200/nouuid1?pretty' -d '{
		"settings":{
			"index":{
				"number_of_shards":2, 
				"number_of_replicas":1
			}
		}
	}'

当然，如果要作为全局配置，可以放入模板中，后面会介绍。

**分片的分配和移动**是ES自身经常使用的功能，由两个参数控制，一般情况下必须开启这两个参数：

	curl -XPUT http://127.0.0.1:9200/_cluster/settings -d '{
      "transient": {
        "cluster.routing.allocation.enable": "all",
        "cluster.routing.rebalance.enable": "all"
      }
    }'

cluster.routing.allocation.enable不开启，ES无法创建index。cluster.routing.rebalance.enable，ES无法移动分片。

涉及到分片移动的，都需要先开启分片移动，比如**手动移动分片**，需要先开启分片移动，然后移动shard：

	curl -XPOST 'http://127.0.0.1:9200/_cluster/reroute?pretty' -d '{
		"commands": [ {
			"move":{
				"index": "nouuid1", 
				"shard" : 0,
				"from_node": "es5-test-node-3", 
				"to_node" : "es5-test-node-4"
			}
		}]
	}'

移动完后根据情况判断是否需要关闭分片移动。

冷热分离的架构上定时的 **热节点分片向冷节点移动**，也需要先开启分片移动，然后：

	curl -XPUT 'http://127.0.0.1:9200/nouuid1/_settings?pretty' -d '{"settings":{"index.routing.allocation.require.box_type":"warm"}}'
	curl -XPUT 'http://127.0.0.1:9200/nouuid2/_settings?pretty' -d '{"settings":{"index.routing.allocation.require.box_type":"warm"}}'

冷数据节点压力不是太大，其实可以作为**segment优化合并**的场所，在段合并时主从分片都会进行合并，会造成系统压力，建议先删除从片，合并后再添加从片：

	curl -XPUT 'http://127.0.0.1:9200/test1-y-20170420/_settings?pretty' -d '{"number_of_replicas": 0}'
	curl -XPOST 'http://127.0.0.1:9200/test1-y-20170420/_forcemerge?max_num_segments=1&pretty'
	curl -XPUT 'http://127.0.0.1:9200/test1-y-20170420/_settings?pretty' -d '{"number_of_replicas": 1}'

但有时候是不需要开启这两个参数，比如**重启集群**，重启之前，先关闭ElasticSearch集群的高可用和自平衡方案功能，避免重启之后，长时间的分片平衡。

	curl -XPUT http://127.0.0.1:9200/_cluster/settings -d '{
      "transient": {
        "cluster.routing.allocation.enable": "none",
        "cluster.routing.rebalance.enable": "none"
      }
    }' 

重启之后，再判断是否需要打开分片平衡功能。重启时伴随着索引恢复，以下索引恢复相关的默认配置如下，建议不要改动：

    indices.recovery.file_chunk_size: 512kb
    indices.recovery.translog_ops: 1000
    indices.recovery.translog_size: 512kb
    indices.recovery.compress: true
    indices.recovery.max_bytes_per_sec: 40mb

索引的前提是将文档通过分词器进行分词，ES提供了很多默认的分词器，可以满足大部分需求，但有时候我们需要使用定制化的分词，这时就需要先配置**申明分词器**。比如下面申明了pinyin_analyzer分词器，使用了pinyin分词：

	curl -XPUT "http://127.0.0.1:9200/nouuid?pretty" -d '{
	  "index" : {
	        "analysis" : {
	            "analyzer" : {
					"pinyin_analyzer": {
						"type": "custom",
						"tokenizer": "my_pinyin",
						"filter" : "word_delimiter"
					}
	            },
	            "tokenizer" : {
	                "my_pinyin" : {
	                    "type" : "pinyin",
	                    "first_letter" : "none",
	                    "padding_char" : " "
	                }
	            }
	        }
	    }
	}'

然后再使用分词器指明文档的某个字段使用该分词器：

	curl -XPOST "http://127.0.0.1:9200/nouuid/folks/_mapping?pretty" -d '{
	  "folks": {
	        "properties": {
	            "name": {
	                "type": "string",
	                "fields": {
	                    "type": "string",
	                    "analyzer": "pinyin_analyzer"
	                }
	            }
	        }
	    }
	}'

在nouuid上写入folks类型的文档时，就会按照pinyin_analyzer进行分词。

上面是对name字段使用了拼音分词，但业务往往是复杂的，有时候需要**一份数据创建多份索引**，比如要name字段建立keword和pinyin两种索引，这时我们要在字段上mapping两个分词器。做法是在名称为nouuid的index的folks类型上添加mapping，同时在name域建立两个别名kw和py，分别使用keyword分词和pinyin_analyzer分词：

	curl -XPOST "http://127.0.0.1:9200/nouuid/folks/_mapping?pretty" -d '{
	  "folks": {
	        "properties": {
	            "name": {
	                "type": "string",
	                "fields": {
	                	"kw": {
							"type": "string",
							"analyzer": "keyword"
	                	},
	                    "py": {
	                        "type": "string",
	                        "analyzer": "pinyin_analyzer"
	                    }
	                }
	            }
	        }
	    }
	}'

写入数据：

	curl -XPOST 'http://127.0.0.1:9200/nouuid/folks/1?pretty' -d '{
	  "name":"刘德华"
	}'

通过下面语句都能查到数据了：

	curl -XPOST 'http://127.0.0.1:9200/nouuid/folks/_search?pretty' -d '{
	  "query": {
	    "multi_match": {
	      "type": "most_fields", 
	      "query": "刘德华",
	      "fields": [ "name" ]
	    }
	  }
	}'

	curl -XPOST 'http://127.0.0.1:9200/nouuid/folks/_search?pretty' -d '{
	  "query": {
	    "multi_match": {
	      "type": "most_fields", 
	      "query": "刘德华",
	      "fields": [ "name.kw" ]
	    }
	  }
	}'

	curl -XPOST 'http://127.0.0.1:9200/nouuid/folks/_search?pretty' -d '{
	"query": {
		"multi_match": {
			"type": "most_fields", 
			"query": "德华",
			"fields": [ "name.py" ]
		}
	}
	}'

	curl -XPOST 'http://127.0.0.1:9200/nouuid/folks/_search?pretty' -d '{
		"query": {
			"multi_match": {
				"type": "most_fields", 
				"query": "刘德华",
				"fields": [ "name", "name.kw", "name.py" ]
			}
		}
	}'

上面是在某个index上进行设置，如果要扩展到一类index上，可以**创建template**。template是一系列settings和mappings的组合，ES创建的索引符合模板类型时，将按照模板创建索引。

	curl -XPOST '127.0.0.1:9200/_template/logs?pretty' -d '
	{  
		"template":"nouuid*",
		"settings":{
			"index":{
				"number_of_shards":2, 
				"number_of_replicas":1
			},
			"index.routing.allocation.require.box_type":"hot",
			"analysis":{  
				"char_filter":{  
					"my_pattern":{  
						"type":"pattern_replace",
						"pattern":"\\n",
						"replacement":" "
					}
				},
				"tokenizer":{  

				},
				"filter":{  
					"my_word_delimiter":{  
						"type":"word_delimiter",
						"split_on_case_change":false,
						"generate_word_parts":true,
						"generate_number_parts":true,
						"catenate_words":false,
						"catenate_numbers":false,
						"catenate_all":false,
						"preserve_original":true
					},
					"log_word_delimiter":{  
						"type":"word_delimiter",
						"generate_word_parts":true,
						"generate_number_parts":true,
						"catenate_words":false,
						"catenate_numbers":false,
						"catenate_all":false,
						"preserve_original":false
					},
					"pattern_replace_filer":{  
						"type":"pattern_replace",
						"pattern":"^[A-Za-z0-9]{1,2}$",
						"replacement":" "
					},
					"stop_filer":{  
						"type":"stop",
						"stopwords":"_english_"
					}
				},
				"analyzer":{  
					"text_wdf":{  
						"type":"custom",
						"char_filter":[  

						],
						"tokenizer":"standard",
						"filter":[  
							"lowercase",
							"my_word_delimiter"
						]
					},
					"text_log":{  
						"type":"custom",
						"char_filter":[  
							"my_pattern",
							"html_strip"
						],
						"tokenizer":"ik_max_word",
						"filter":[  
							"lowercase",
							"log_word_delimiter",
							"pattern_replace_filer",
							"stop_filer"
						]
					}
				}
			}
		},
		"mappings":{  
			"log":{  
				"_source":{  
					"includes":[  

					],
					"excludes":[  

					]
				},
				"_all":{  
					"enabled":false
				},
				"properties":{  
					"id":{  
						"type":"string",
						"analyzer":"keyword",
						"fielddata": true
					},
					"interfaceName":{  
						"type":"string",
						"analyzer":"text_wdf",
						"fielddata": true
					},
					"flag":{  
						"type":"boolean"
					},
					"type":{  
						"type":"integer"
					},
					"log":{  
						"type":"string",
						"analyzer":"text_log",
						"fielddata": true
					}
				}
			}
		}
	}
	'

# 内存管理

ES内存消耗大户主要包括：Segment Memory、Node Query Cache、Field Data cache、Shard Request Cache、Bulk Queue、Indexing Buffer、Cluster State Buffer、超大搜索聚合结果集的fetch。

**（1）Segment Memory**。segment是一个完备的lucene倒排索引，而倒排索引是通过词典(Term Dictionary)到文档列表(Postings List)的映射关系，快速做查询的。由于词典的size会很大，全部装载到heap里不现实，因此Lucene为词典做了一层前缀索引(Term Index)，这个索引在Lucene4.0以后采用的数据结构是FST (Finite State
Transducer)。这种数据结构占用空间很小，Lucene打开索引的时候将其全量装载到内存中，加快磁盘上词典查询速度的同时减少随机磁盘访问次数。

因此，ES的data node存储数据并非只是耗费磁盘空间的，为了加速数据的访问，每个segment都有会一些索引数据驻留在heap里。因此segment越多，瓜分掉的heap也越多，并且这部分heap是**无法被GC掉**的！理解这点对于监控和管理集群容量很重要，当一个node的segment memory占用过多的时候，就需要考虑删除、归档数据，或者扩容了。

使用CAT API可以知道segment memory占用情况：

	* 查看一个索引所有segment的memory占用情况；
	* 查看一个node上所有segment占用的memory总和。 curl 'http://localhost:9200/_cat/segments?v'

减少data node上的segment memory占用的途径，总结起来有三种方法：

	* 删除不用的索引。
	* 关闭索引（文件仍然存在于磁盘，只是释放掉内存）。需要的时候可以重新打开。
	* 定期对不再更新的索引做合并，可以节省大量的segment memory。

**（2）Node Query Cache**

* 查询缓存（Node Query Cache）只在filter语义中缓存查询结果。
* 每个node上所有的shard共用一个查询缓存。
* 使用LRU作为其失效策略：缓存满，则驱逐最近使用的数据。
* 需要注意的是这个缓存也是常驻heap，无法GC的。
* 默认配置为10% heap size，实际使用中heap没什么压力的情况下，才考虑加大这个设置。

配置：

    index.queries.cache.enabled: true //默认为true
    indices.queries.cache.size: 10%  //默认为堆大小10%

**（3）Field Data Cache**

* 字段缓存（Field Data Cache）主要在字段sort或者 aggregating时被用到。
* 缓存时，ES会把这个字段的所有值都load出来，所以一定要确保有足够的缓存处理它，并且使缓存一直保留，避免多次重建。
* 2.0以前的版本主要依赖这个cache缓存已经计算过的数据，提升性能。但是由于heap空间有限，当遇到用户对海量数据做计算的时候，就很容易导致heap吃紧，集群频繁GC，根本无法完成计算过程。
* 需要限制对field data cache的使用，最好是完全不用，可以极大释放heap压力。这里需要注意的是，排序、聚合字段必须为not analyzed。设想如果有一个字段是analyzed过的，排序的实际对象其实是词典，在数据量很大情况下这种情况非常致命。

> ES2.0以后，正式默认启用[Doc Values技术](https://www.elastic.co/guide/en/elasticsearch/reference/current/doc-values.html)。在构建索引时，将field data创建到硬盘上，达到比之前采用field data cache机制更好的性能。

配置：

    indices.fielddata.cache.size: 30%    // 默认是unbounded

需要使用field data cache时，可以配合Circuit Breaker（熔断器）来避免诸如导致OOM这类的情景的操作。

不配置indices.fielddata.cache.size，es默认不对缓存进行回收，缓存到达短路线indices.breaker.fielddata.limit（默认80%）的限制大小，则无法插入数据，报错。

需要规划的目标为：

* 当前fieldData缓存区大小 < indices.fielddata.cache.size 
* 当前fieldData缓存区大小+下一个查询加载进来的fieldData < indices.breaker.fielddata.limit 
* 设置indices.fielddata.cache.size，则缓存大小到达indices.fielddata.cache.size时开始进行回收。


监控API：

    curl -XGET 'http://localhost:9200/_nodes/stats'
    curl -XGET 'http://localhost:9200/_stats/fielddata/?fields=field1,field2&pretty'
    
**（4）Shard Request Cache**

在一个有多个shard的index上进行搜索时，首先每个shard本地执行，然后将结果返回给协调节点，合并成一个完整的结果集。

[shard-level的cache缓存](https://www.elastic.co/guide/en/elasticsearch/reference/current/shard-request-cache.html)就是缓存每个shard的本地结果的。

需要要注意的是： 当size=0时，该cache只缓存count类型（如hits.total, aggregations, and suggestions）的搜索，不会缓存 hits。

shard刷新时（shard中的数据确实发生改变），已缓存的结果会自动失效，除非。

刷新时间间隔越长，缓存数据保持有效的时间越长。如果缓存满了，最近使用的数据被驱逐。

配置：

    index.requests.cache.enable: true //默认配置是true
    indices.requests.cache.size: 2%  //默认配置 1%

API

    curl -XPUT localhost:9200/my_index/_settings -d'{ "index.requests.cache.enable": true }'


监控（查看cache大小和驱逐数据数量）：

    curl '10.9.19.143:9200/_stats/request_cache?pretty&human' //按index显示
    curl '10.9.19.143:9200/_node/_stats/indices/request_cache?pretty&human' //按node显示

**（5）Bulk Queue**

当所有的bulk thread都在忙，无法响应新的bulk request的时候，将request在内存里排列起来，然后慢慢清掉。

一般来说，Bulk queue不会消耗很多的heap，但是见过一些用户为了提高bulk的速度，客户端设置了很大的并发量，并且将bulk Queue设置到不可思议的大，比如好几千。

这在应对短暂的请求爆发的时候有用，但是如果集群本身索引速度一直跟不上，设置的好几千的queue都满了会是什么状况呢？ 取决于一个bulk的数据量大小，乘上queue的大小，heap很有可能就不够用，内存溢出了。

一般来说官方默认的thread
pool设置已经能很好的工作了，建议不要随意去“调优”相关的设置，很多时候都是适得其反的效果。


**（6）Indexing Buffer**

Indexing Buffer是用来缓存新数据，当其满了或者refresh/flush interval到了，就会以segment file的形式写入到磁盘。

这个参数的默认值是10% heap size。根据经验，这个默认值也能够很好的工作，应对很大的索引吞吐量。

但有些用户认为这个buffer越大吞吐量越高，因此见过有用户将其设置为40%的。到了极端的情况，写入速度很高的时候，40%都被占用，导致OOM。

配置：

    indices.memory.index_buffer_size: 10%       //默认10% JVM heap
    indices.memory.min_index_buffer_size: 48mb  //默认48mb
    indices.memory.max_index_buffer_size: 20%      //默认unbounded

**（7）Cluster State Buffer**

ES被设计成每个Node都可以响应用户的api请求，因此每个Node的内存里都包含有一份集群状态的拷贝。这个Cluster state包含诸如集群有多少个Node，多少个index，每个index的mapping是什么？有少shard，每个shard的分配情况等等(ES有各类stats api获取这类数据)。

在一个规模很大的集群，这个状态信息可能会非常大的，耗用的内存空间就不可忽视了。并且在ES2.0之前的版本，state的更新是由Master Node做完以后全量散播到其他结点的。频繁的状态更新都有可能给heap带来压力。

在超大规模集群的情况下，可以考虑分集群并通过tribe node连接做到对用户api的透明，这样可以保证每个集群里的state信息不会膨胀得过大。


**（8）超大搜索聚合结果集的fetch**

ES是分布式搜索引擎，搜索和聚合计算除了在各个data node并行计算以外，还需要将结果返回给汇总节点进行汇总和排序后再返回。无论是搜索，还是聚合，如果返回结果的size设置过大，都会给heap造成很大的压力，特别是数据汇聚节点。

这么多缓存会大量占用系统内存，导致一些功能受损，因此ES通过**断路器**控制缓存的大小：

	# 父断路器
    indices.breaker.total.limit: 70%  // 默认的设置是70%

	# 字段数据断路器
    indices.breaker.fielddata.limit: 60% // 默认值为JVM heap的60%
    indices.breaker.fielddata.overhead: 1.03 //可超出值，默认1.03
        
	# 预加载数据请求断路器
    indices.breaker.request.limit: 60%  // 默认的设置是60%
    indices.breaker.request.overhead: 1  // 默认为1
        
	# 请求断路器
    network.breaker.inflight_requests.limit: 100%  // 默认的设置是100% JVM heap
    network.breaker.inflight_requests.overhead: 1 //默认为1
        
	# 脚本编译断路器
    script.max_compilations_per_minute: 15 //动态编译脚本数，默认15

如果断路器工作不理想，还可以定期手动进行**缓存清理**：

	# 清理所有的cache
    curl -XPOST 'http://localhost:9200/twitter/_cache/clear'
    
	# 清理指定类型cache
    curl -XPOST 'localhost:9200/kimchy,elasticsearch/_cache/clear?request_cache=true'
    
	# 清理指定index的cache
    curl -XPOST 'http://localhost:9200/kimchy,elasticsearch/_cache/clear'

# 集群动态配置

查看配置

	curl -XGET 'http://127.0.0.1:9200/_cluster/settings?pretty'

集群动态配置时，transient是临时的，persistent是永久的。这是一个全局配置，一般建议用persistent，保证集群异常恢复后和异常前的配置一致。

	curl -XPUT 'http://127.0.0.1:9200/_cluster/settings?pretty' -d '{
	    "persistent" : {
	        "cluster.routing.allocation.enable": "all",
	        "cluster.routing.rebalance.enable": "all",
	        "search.default_search_timeout": "250s",
	        "indices.breaker.total.limit": "70%",
	        "indices.breaker.fielddata.limit": "60%",
	        "cluster.routing.allocation.disk.watermark.low": "400gb",
        	"cluster.routing.allocation.disk.watermark.high": "500gb",
        	"cluster.info.update.interval": "1m"
	    }
	}'

动态配置有一个很大的坑，transient是临时的，重启后会丢失；persistent是永久的，会将配置写入文件，重启后不会丢失，但如果这个文件被删除了，那所有的配置都没有了。所以千万要注意不要删除这个文件：

	$ES_HOME/$ES_CLUSTER_NAME/nodes/0/_state/global-{number}.st

# 监控和安全

监控的指标主要包括：

集群状态

	curl -XGET 'http://127.0.0.1:9200/_cat/health?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/health?v&pretty&ts=false'
	# 显示master
	curl -XGET 'http://127.0.0.1:9200/_cat/master?v&pretty'
	# node拓扑结构和性能
	curl -XGET 'http://127.0.0.1:9200/_cat/nodes?v&pretty'
	# node的attritude
	curl -XGET 'http://127.0.0.1:9200/_cat/nodeattrs?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/nodeattrs?&h=node,id,pid,host,ip,port,attr,value&v&pretty'

节点状态：

	curl -XGET 'http://10.8.32.37:8101/_nodes?pretty'

显示index状态

	curl -XGET 'http://127.0.0.1:9200/_cat/indices/*?v&pretty'
	# 选择显示的标题
	curl -XGET 'http://127.0.0.1:9200/_cat/indices/*?v&pretty&h=health'
	# 按条件过滤数据
	curl -XGET 'http://127.0.0.1:9200/_cat/indices/*?v&pretty&health=yellow'
	# 按结果字段排序
	curl -XGET 'http://127.0.0.1:9200/_cat/indices/*?v&pretty&s=docs.count:desc'
	curl -XGET 'http://127.0.0.1:9200/_cat/indices/*?v&pretty&s=index:desc'
	# 存在的别名
	curl -XGET 'http://127.0.0.1:9200/_cat/aliases?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/aliases/alias1?v&pretty'

shard监控

	# shard状态
	curl -XGET 'http://127.0.0.1:9200/_cat/shards?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/shards/[indexname]?v&pretty'
	# shard分布情况
	curl -XGET 'http://127.0.0.1:9200/_cat/allocation?v&pretty'
	# 段状态
	curl -XGET 'http://127.0.0.1:9200/_cat/segments/indexname?v&pretty'

document

	# document数量统计：
	curl -XGET 'http://127.0.0.1:9200/_cat/count?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/count/indexname?v&pretty'
	

snapshot

	# 有哪些snapshot仓库
	curl -XGET 'http://127.0.0.1:9200/_cat/repositories?v&pretty'
	# 某个仓库中的有哪些snapshot
	curl -XGET 'http://127.0.0.1:9200/_cat/snapshots/[repositoryname]?v&pretty'
	# snapshot恢复过程
	curl -XGET 'http://127.0.0.1:9200/_cat/recovery?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/recovery?h=i,s,t,ty,st,shost,thost,f,fp,b,bp&v&pretty'

线程

	# 线程池
	curl -XGET 'http://127.0.0.1:9200/_cat/thread_pool?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cat/thread_pool/[threadpoolname]?v&pretty'
	# 查看当前**热门线程**：
	curl -XGET 'http://127.0.0.1:9200/_nodes/hot_threads'

内存

	# 缓存
	curl -XGET 'http://127.0.0.1:9200/_cat/fielddata?v&pretty'

其他

	# 有哪些模板
	curl -XGET 'http://127.0.0.1:9200/_cat/templates?v&pretty'
	# pending_tasks
	curl -XGET 'http://127.0.0.1:9200/_cat/pending_tasks?v&pretty'
	curl -XGET 'http://127.0.0.1:9200/_cluster/pending_tasks?pretty'
	# 插件
	curl -XGET 'http://127.0.0.1:9200/_cat/plugins?v&pretty'

**安全**使用nginx方案。



# 运维管理

自动化运维工具 //TODO

在实际运维过程，有时候会遇到机房迁移等需要进行**集群迁移**的情况。

最简单的是**停机迁移**，直接关掉集群，将旧集群直接copy一份到新集群中，启动新集群即可。

或者停掉服务，采用[**elasticdump**](https://github.com/taskrabbit/elasticsearch-dump)或**快照迁移**。elasticdump是一次性将index迁移到新集群。快照迁移是利用快照恢复数据，中间需要经历共享存储，更加耗时。更重要的是，这些方法都不使用于不能停服务的生产环境。

