package com.jengine.data.nosql.hdfs;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * content
 *
 * @author nouuid
 * @date 11/29/2016
 * @since 0.1.0
 */
public class HdfsDemo {

  private HdfsService hdfsService;

  @org.junit.Before
  public void before() throws IOException {
    Configuration config = new Configuration();
//        config.set("io.compression.codecs", "org.apache.hadoop.io.compress.SnappyCodec");
    String hdfsPath = "hdfs://test-storm-master.800best.com:9000";
//        String hdfsPath = "hdfs://127.0.0.1:9000";
    hdfsService = new HdfsService(hdfsPath, config);
  }

  @org.junit.After
  public void after() {

  }

  /**
   * 上传本地文件到文件系统
   */
  @org.junit.Test
  public void uploadLocalFile2HDFS() throws IOException {
    String source = "e://x.txt";
    String dst = "/es1";
    hdfsService.uploadLocalFile2HDFS(source, dst);
  }

  @org.junit.Test
  public void deleteDir() throws IOException {
    String dir = "/es1";
    hdfsService.deleteDir(dir);
  }

  @org.junit.Test
  public void defaultCompressCreate() throws IOException {
    String src = "E:\\xingng_test\\2gc-1104.log";
    String des = "/nouuid1";
    hdfsService.defaultCompressCreate(src, des);
  }

  @org.junit.Test
  public void compressCreate() throws Exception {
    String src = "E:\\xingng_test\\2gc-1104.log";
    String des = "/a_snappy";
//        String codecClassName = "org.apache.hadoop.io.compress.DefaultCodec";
//        String codecClassName = "org.apache.hadoop.io.compress.GzipCodec";
    String codecClassName = "org.apache.hadoop.io.compress.BZip2Codec";
//        String codecClassName = "org.apache.hadoop.io.compress.SnappyCodec"; // need to install snappy
    hdfsService.compressCreate(src, des, codecClassName);
  }

  @org.junit.Test
  public void uncompress() throws Exception {
    String hdfsFilePath = "/es1/indices/test2/0/__0";
    String codecClassName = "org.apache.hadoop.io.compress.BZip2Codec";
    hdfsService.uncompress(hdfsFilePath, codecClassName);
  }

  @org.junit.Test
  public void uncompress2() throws Exception {
    String hdfsFilePath1 = "/es1/indices/test2/0/__0";
    String hdfsFilePath2 = "/es1/indices/test2/0/__1";
    String codecClassName = "org.apache.hadoop.io.compress.BZip2Codec";
    Class<?> codecClass = Class.forName(codecClassName);
    Configuration configuration = new Configuration();
    CompressionCodec codec = (CompressionCodec) ReflectionUtils
        .newInstance(codecClass, configuration);
//        hdfsService.uncompress2(codec, hdfsFilePath1, "e://2.x");
    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          hdfsService.uncompress2(codec, hdfsFilePath1, "e://1.x");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          hdfsService.uncompress2(codec, hdfsFilePath2, "e://2.x");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    thread1.start();
    thread2.start();
    Thread.sleep(60 * 1000 * 30);

  }

//    public static void main(String[] args) throws Exception {
////        Configuration config = new Configuration();
//////        config.set("io.compression.codecs", "org.apache.hadoop.io.compress.SnappyCodec");
////        String hdfsPath = "hdfs://test-storm-master.800best.com:9000";
////        HdfsService hdfsService = new HdfsService(hdfsPath, config);
////
////        String src = "E:\\xingng_test\\2gc-1104.log";
////        String des = "/a_snappy";
////        String codecClassName = "org.apache.hadoop.io.compress.SnappyCodec";
////        hdfsService.compressCreate(src, des, codecClassName);
//
//        System.loadLibrary("snappy64");
//        System.out.println(NativeCodeLoader.isNativeCodeLoaded());
//        System.out.println(NativeCodeLoader.buildSupportsSnappy());
//        System.out.println("xxxxxxxx");
//    }

  @org.junit.Test
  public void test() {
//        System.setProperty("java.library.path", "E:\\opthadoop-commonhadoop-common-2.2.0-bin-master\\bin");
    System.loadLibrary("hadoop");
  }

}
