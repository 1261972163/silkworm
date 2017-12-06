package com.jengine.common2.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

/**
 * @author nouuid
 */
public class ChangeClassRunDemo {
  public static String sourceClassPath = "com/jengine/common2/asm/C";
  public static String classFolderPath = "e://test/asmtest";
  public static String destinationClassPath = classFolderPath + "/" + sourceClassPath.substring(sourceClassPath.lastIndexOf("/")) + ".class";
  public String classNewFullName = "com.jengine.common2.asm.test.C";

  public static void main(String[] args) throws Exception {
    ChangeClassRunDemo changeClassRunDemo = new ChangeClassRunDemo();
    changeClassRunDemo.createNewClass();
    changeClassRunDemo.runNewClass();
  }

  private void createNewClass() {
    try {
      ClassReader cr = new ClassReader(sourceClassPath);
      ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
      ClassAdapter classAdapter = new AddTimeClassAdapter(cw);
      //使给定的访问者访问Java类的ClassReader
      cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
      byte[] data = cw.toByteArray();
      File file = new File(destinationClassPath);
      FileOutputStream fout = new FileOutputStream(file);
      fout.write(data);
      fout.close();
      System.out.println("create success!");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void runNewClass() throws InterruptedException, NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException, ClassNotFoundException,
      InstantiationException, MalformedURLException, NoSuchMethodException,
      InvocationTargetException {

    MyClassLoader loader1 = new MyClassLoader(classFolderPath);
    Class<?> clazz = loader1.loadClass(classNewFullName);//加载类，得到Class对象
    Object obj = clazz.newInstance();//初始化一个实例
    Method method2 = clazz.getMethod("m");//方法名和对应的参数类型
    method2.invoke(obj);//调用得到的上边的方法method
    System.out.println("end...");
  }

  class MyClassLoader extends ClassLoader {

    private String classFolderPath;//类存放的路径

    MyClassLoader(String classFolderPath) {
      this.classFolderPath = classFolderPath;
    }

    @Override
    public Class<?> findClass(String name) {
      byte[] data = loadClassData(name);
      return this.defineClass(name, data, 0, data.length);
    }

    public byte[] loadClassData(String name) {
      try {
        String classSimplName = name.substring(name.lastIndexOf(".") + 1);
        String classPath = classFolderPath + "/" + classSimplName + ".class";
        FileInputStream is = new FileInputStream(new File(classPath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b = 0;
        while ((b = is.read()) != -1) {
          baos.write(b);
        }
        return baos.toByteArray();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
  }
}


