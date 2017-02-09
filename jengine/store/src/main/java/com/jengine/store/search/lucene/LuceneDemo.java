package com.jengine.store.search.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.BaseDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Lucene demo
 * 1、创建内存目录对象Directory；
 * 2、创建索引写入器IndexWriter，该对象既可以把索引写入到磁盘中也可以写入到内存中。
 * 3、利用IndexWriter将指定的数据存入内存目录对象中；
 * <3> 创建IndexSearch 索引查询对象，然后根据关键字封装Query查询对象；
 * <4> 调用search()方法，将查询的结果返回给ScoreDoc ，迭代里面所有的Document对象，显示查询结果；
 * <5> 关闭IndexWriter 写入器，关闭RAMDirectory目录对象。
 *
 * @author bl07637
 * @date 10/25/2016
 * @since 0.1.0
 */
public class LuceneDemo {

    private BaseDirectory directory = null;
    private IndexWriter   writer    = null;

    StandardAnalyzer analyzer = new StandardAnalyzer();

    public LuceneDemo() {
        init(true);
    }

    private void init(boolean isMemory) {
        // 创建内存目录对象
        try {
            if (isMemory) {
                directory = new RAMDirectory(); //生成的索引放在内存中
            } else {
                String osName = System.getProperty("os.name");
                String path = null;
                if (osName.toLowerCase().startsWith("windows")) {
                    path = "c://luceneTest";
                } else {
                    path = "/tmp/luceneTest";
                }
                directory = new NIOFSDirectory(Paths.get(path)); //生成的索引放在磁盘中
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建索引
     * @throws IOException
     */
    public void index() throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try {
            writer = new IndexWriter(directory, indexWriterConfig);
            addDoc(writer, "Lucene in Action", "193398817");
            addDoc(writer, "Lucene for Dummies", "55320055Z");
            addDoc(writer, "Managing Gigabytes", "55063554A");
            addDoc(writer, "The Art of Computer Science", "9900333X");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    private void addDoc(IndexWriter indexWriter, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        indexWriter.addDocument(doc);
    }

    /**
     * 查询索引
     * @throws ParseException
     * @throws IOException
     */
    public void search() throws ParseException, IOException {
        // query
        String querystr = "lucene";
        Query query = new QueryParser("title", analyzer).parse(querystr);

        // search
        int hitsPerPage = 10;
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // display results
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        // reader can only be closed when there is no need to access the documents any more.
        indexReader.close();
    }

    public static void main(String[] args) throws IOException, ParseException {
        LuceneDemo luceneDemo = new LuceneDemo();
        luceneDemo.index();
        luceneDemo.search();
    }

}
