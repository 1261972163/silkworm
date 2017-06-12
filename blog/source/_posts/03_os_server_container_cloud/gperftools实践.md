---
title: gperftools实践
categories: os
tags: 
  - gperftools
  - 性能分析
date: 2016/11/3 17:37:25
---

gperftools是Google提供的一套工具，其中的一个功能是CPU profiler，用于分析程序性能，找到程序的性能瓶颈。

# 1.安装libunwind库

可以从http://download.savannah.gnu.org/releases/libunwind下载相应的libunwind版本，这里下载的是libunwind-0.99-beta.tar.gz，安装过程如下

    [root@localhost home]#tar zxvf libunwind-0.99-beta.tar.gz
    [root@localhost home]# cd libunwind-0.99-beta/
    [root@localhost libunwind-0.99-beta]#./configure
    [root@localhost libunwind-0.99-beta]#make
    [root@localhost libunwind-0.99-beta]#make install
    [root@localhost libunwind-0.99-beta]#make clean


# 2.安装google-perftools

可以从https://github.com/gperftools/gperftools下载相应的google-perftools版本，这里下载的是google-perftools-2.5.tar.gz，安装过程如下:

    [root@localhost home]# tar zxvf google-perftools-2.5.tar.gz
    [root@localhost home]# cd google-perftools-2.5/
    [root@localhost google-perftools-2.5]# ./configure
    [root@localhost google-perftools-2.5]# make
    [root@localhost google-perftools-2.5]# make install
    [root@localhost google-perftools-2.5]# make clean
    [root@localhost google-perftools-2.5]# echo "/usr/local/lib" > /etc/ld.so.conf.d/usr_local_lib.conf
    [root@localhost google-perftools-2.5]# ldconfig

至此，google-perftools安装完成。

# 3.为google-perftools添加线程目录

创建一个线程目录，这里将文件放在/tmp/tcmalloc下，操作如下:

    [root@localhost home]#mkdir /tmp/tcmalloc
    [root@localhost home]#chmod 0777 /tmp/tcmalloc

# 4.修改tomcat启动文件startup.sh

    加入export LD_PRELOAD=/usr/local/lib/libtcmalloc.so
    加入export HEAPPROFILE=/tmp/tcmalloc

重启tomcat，完成google-perftools的加载。

6.

# 7.分析日志

    [root@localhost google-perftools-2.5]# /usr/local/bin/pprof --text $JAVA_HOME/bin/java /tmp/tcmalloc_21656.*.heap

    Total: 1774.3 MB
     936.2  52.8%  52.8%    936.2  52.8% updatewindow
     601.3  33.9%  86.7%    601.3  33.9% os::malloc
     204.3  11.5%  98.2%    204.3  11.5% inflateInit2_
      18.0   1.0%  99.2%     18.0   1.0% init
      10.8   0.6%  99.8%     10.8   0.6% readCEN
       2.5   0.1%  99.9%    206.8  11.7% Java_java_util_zip_Inflater_init
       0.3   0.0% 100.0%      0.3   0.0% CCalloc
       0.3   0.0% 100.0%     11.4   0.6% ZIP_Put_In_Cache0
       0.2   0.0% 100.0%      0.2   0.0% newEntry
       0.1   0.0% 100.0%      0.1   0.0% strdup
       0.1   0.0% 100.0%      0.1   0.0% _dl_new_object
       0.1   0.0% 100.0%      0.1   0.0% _dl_allocate_tls
       0.0   0.0% 100.0%      0.0   0.0% read_alias_file
       0.0   0.0% 100.0%      0.0   0.0% _nl_intern_locale_data
       0.0   0.0% 100.0%      1.6   0.1% VerifyClassForMajorVersion
       0.0   0.0% 100.0%      0.0   0.0% _dl_check_map_versions
       0.0   0.0% 100.0%      0.0   0.0% new_bucket
       0.0   0.0% 100.0%      0.0   0.0% __new_exitfn
       0.0   0.0% 100.0%      0.0   0.0% nss_parse_service_list
       0.0   0.0% 100.0%      0.0   0.0% getprotobyname
       0.0   0.0% 100.0%      0.0   0.0% getpwuid
       0.0   0.0% 100.0%      0.0   0.0% _dl_map_object_deps
       0.0   0.0% 100.0%      0.0   0.0% initialize_class_hash
       0.0   0.0% 100.0%      0.0   0.0% make_class_info_from_name
       0.0   0.0% 100.0%      0.0   0.0% _dl_scope_free
       0.0   0.0% 100.0%      0.0   0.0% _nl_make_l10nflist
       0.0   0.0% 100.0%      0.0   0.0% __nss_database_lookup
       0.0   0.0% 100.0%      0.0   0.0% initLoopbackRoutes
       0.0   0.0% 100.0%      0.0   0.0% JLI_MemAlloc
       0.0   0.0% 100.0%      0.0   0.0% __add_to_environ
       0.0   0.0% 100.0%      0.0   0.0% check_and_push
       0.0   0.0% 100.0%      0.0   0.0% __tzfile_read
      ...

    各字段的含义依次是：
    1. 采样点落在该函数中的次数
    2. 采样点落在该函数中的百分比
    3. 上一项的累积百分比
    4. 采样点落在该函数，以及被它调用的函数中的总次数
    5. 采样点落在该函数，以及被它调用的函数中的总次数百分比
    6. 函数名

# 8.btrace

    [root@xingngtest1 btrace]#vim /opt/gperftools/btrace/samples/Wy.java

    import static com.sun.btrace.BTraceUtils.*;
    import com.sun.btrace.annotations.*;

    import java.nio.ByteBuffer;
    import java.lang.Thread;

    @BTrace public class BtracerInflater{
       @OnMethod(
          clazz="java.util.zip.Inflater",
          method="/.*/"
       )
       public static void traceCacheBlock(){
          println("Who call java.util.zip.Inflater's methods :");
         jstack();
       }
    }

    [root@xingngtest1 btrace]#/opt/gperftools/btrace/bin/btrace -cp /opt/gperftools/btrace/build 17873 /opt/gperftools/btrace/samples/Wy.java | more



