package com.jengine.data.nosql.hdfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * content
 *
 * @author nouuid
 * @date 6/5/2017
 * @since 0.1.0
 */
public class HdfsService {

  private FileSystem hdfs;
  private Configuration configuration;

  public HdfsService(String hdfsPath, Configuration configuration) {
    try {
      this.configuration = configuration;
      hdfs = FileSystem.get(URI.create(hdfsPath), configuration);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //------------------------------------------文件操作----------------------------------------

  public void readFileByLines(String fileName) {
    File file = new File(fileName);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      int line = 1;
      // 一次读入一行，直到读入null为文件结束
      while ((tempString = reader.readLine()) != null) {
        // 显示行号
        System.out.println("line " + line + ": " + tempString);
        line++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
        }
      }
    }
  }

  /**
   * 读取指定文件，并采用deflate压缩写入hdfs
   */
  public void defaultCompressCreate(String src, String des) throws IOException {
    Path path = new Path(des);
    OutputStream outputStream = hdfs.create(path);
    DefaultCodec codec = new DefaultCodec();
    codec.setConf(new Configuration());
    CompressionOutputStream compressedOutput = codec.createOutputStream(outputStream);

    File file = new File(src);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String tempString;
    while ((tempString = reader.readLine()) != null) {
      compressedOutput.write(tempString.getBytes());
    }
    reader.close();

    hdfs.close();
  }

  /**
   * 读取指定文件，并采用压缩写入hdfs
   *
   * @param src 本地文件路径
   * @param des hdfs文件路径
   * @param codecClassName 压缩类型，类全限定名
   */
  public void compressCreate(String src, String des, String codecClassName) throws Exception {
    // 指定hdfs文件路径
    FSDataOutputStream outputStream = hdfs.create(new Path(des));
    // 指定压缩类型
    Class<?> codecClass = Class.forName(codecClassName);
    Configuration configuration = new Configuration();
    CompressionCodec codec = (CompressionCodec) ReflectionUtils
        .newInstance(codecClass, configuration);
    // 创建压缩输出流
    CompressionOutputStream out = codec.createOutputStream(outputStream);

    // 读源文件
    File file = new File(src);
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String tempString;
    while ((tempString = reader.readLine()) != null) {
      out.write(tempString.getBytes());
    }
    // 结束
    reader.close();
    IOUtils.closeStream(out);
    hdfs.close();

  }

  public void uncompress(String hdfsFilePath, String codecClassName) throws Exception {
    // 指定hdfs文件路径
    FSDataInputStream inputStream = hdfs.open(new Path(hdfsFilePath));
    // 指定压缩类型
    Class<?> codecClass = Class.forName(codecClassName);
    Configuration configuration = new Configuration();
    CompressionCodec codec = (CompressionCodec) ReflectionUtils
        .newInstance(codecClass, configuration);
    //把text文件里到数据解压，然后输出到控制台
    CompressionInputStream in = codec.createInputStream(inputStream);
    IOUtils.copyBytes(in, System.out, new Configuration());
    IOUtils.closeStream(in);
  }

  public void uncompress2(CompressionCodec codec, String hdfsFilePath, String to) throws Exception {
    // 指定hdfs文件路径
    FSDataInputStream inputStream = hdfs.open(new Path(hdfsFilePath));
    //把text文件里到数据解压，然后输出到控制台
    CompressionInputStream in = codec.createInputStream(inputStream);

    FileOutputStream fileOutputStream = new FileOutputStream(to);
    IOUtils.copyBytes(in, fileOutputStream, new Configuration());
    IOUtils.closeStream(in);
  }


  /**
   * 上传本地文件到文件系统
   */
  public void uploadLocalFile2HDFS(String s, String d) throws IOException {
    Path src = new Path(s);
    Path dst = new Path(d);
    hdfs.copyFromLocalFile(src, dst);
    hdfs.close();
  }

  /**
   * 创建新文件，并写入
   */
  public void createNewHDFSFile(String toCreateFilePath, String content) throws IOException {
    FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));
    os.write(content.getBytes("UTF-8"));
    os.close();
    hdfs.close();
  }

  /**
   * 删除文件
   */
  public boolean deleteHDFSFile(String dst) throws IOException {
    Path path = new Path(dst);
    boolean isDeleted = hdfs.delete(path, true);
    hdfs.close();
    return isDeleted;
  }

  /**
   * 读取文件
   */
  public byte[] readHDFSFile(String dst) throws Exception {
    // check if the file exists
    Path path = new Path(dst);
    if (hdfs.exists(path)) {
      FSDataInputStream is = hdfs.open(path);
      // get the file info to create the buffer
      FileStatus stat = hdfs.getFileStatus(path);

      // create the buffer
      byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
      is.readFully(0, buffer);

      is.close();
      hdfs.close();

      return buffer;
    } else {
      throw new Exception("the file is not found .");
    }
  }

  //-----------------------------------------目录操作-----------------------------------------

  /**
   * 创建目录
   */
  public void mkdir(String dir) throws IOException {
    hdfs.mkdirs(new Path(dir));
    hdfs.close();
  }

  /**
   * 删除目录
   */
  public void deleteDir(String dir) throws IOException {
    hdfs.delete(new Path(dir), true);
    hdfs.close();
  }

  /**
   * 读取某个目录下的所有文件
   */
  public void listAll(String dir) throws IOException {
    FileStatus[] stats = hdfs.listStatus(new Path(dir));

    for (int i = 0; i < stats.length; ++i) {
      if (stats[i].isFile()) {
        // regular file
        System.out.println(stats[i].getPath().toString());
      } else if (stats[i].isDirectory()) {
        // dir
        System.out.println(stats[i].getPath().toString());
      } else if (stats[i].isSymlink()) {
        // is s symlink in linux
        System.out.println(stats[i].getPath().toString());
      }

    }
    hdfs.close();
  }

  /**
   * 从HDFS下载文件或者文件夹的所有内容到本地
   */
  public void downloadFileorDirectoryOnHDFS(String downloadPath, String localPath)
      throws IOException {
    Path download = new Path(downloadPath); // from
    Path localDir = new Path(localPath); // to
    // 从HDFS拷贝到本地目录下
    hdfs.copyToLocalFile(download, localDir);
    hdfs.close();
  }

  /**
   * 重命名HDFS上的文件夹或者文件
   */
  public void renameFileOrDirectoryOnHDFS(String srcPath, String destPath) throws IOException {
    Path src = new Path(srcPath);
    Path dest = new Path(destPath);
    hdfs.rename(src, dest);
    hdfs.close();
  }

  public void countSize(String path) throws IOException {
    Path src = new Path(path);
    hdfs.getContentSummary(src).getFileCount();

  }
}