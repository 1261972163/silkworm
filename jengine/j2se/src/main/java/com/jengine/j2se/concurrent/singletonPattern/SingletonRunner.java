package com.jengine.j2se.concurrent.singletonPattern;

import java.io.*;

/**
 * Created by weiyang on 4/9/16.
 */
public class SingletonRunner {

    public void hungryLoadSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + HungryLoadSingleton.getInstance().hashCode());
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void LazyLoadSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + LazyLoadSingleton.getInstance().hashCode());
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void lazyLoadDCLSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + LazyLoadDCLSingleton.getInstance().hashCode());
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void staticInnerClassSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + StaticInnerClassSingleton.getInstance().hashCode());
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void staticInnerClassSingletonSeralizableMultiInstanceTest()  {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            StaticInnerClassSingletonSeralizableMultiInstance staticInnerClassSeralizableSingleton = StaticInnerClassSingletonSeralizableMultiInstance.getInstance();
            String path = SingletonRunner.class.getResource("/").getPath() + "serializableFile.txt";
            fos = new FileOutputStream(new File(path));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(staticInnerClassSeralizableSingleton);
            System.out.println(staticInnerClassSeralizableSingleton.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            Thread.sleep(5*1000);
            String path = SingletonRunner.class.getResource("/").getPath() + "serializableFile.txt";
            fis = new FileInputStream(new File(path));
            ois = new ObjectInputStream(fis);
            StaticInnerClassSingletonSeralizableMultiInstance staticInnerClassSeralizableSingleton = (StaticInnerClassSingletonSeralizableMultiInstance)ois.readObject();
            System.out.println(staticInnerClassSeralizableSingleton.hashCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void staticInnerClassSingletonSeralizableOneInstanceTest()  {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            StaticInnerClassSingletonSeralizableOneInstance staticInnerClassSingletonSeralizableOneInstance = StaticInnerClassSingletonSeralizableOneInstance.getInstance();
            String path = SingletonRunner.class.getResource("/").getPath() + "serializableFile.txt";
            fos = new FileOutputStream(new File(path));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(staticInnerClassSingletonSeralizableOneInstance);
            System.out.println(staticInnerClassSingletonSeralizableOneInstance.hashCode());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            Thread.sleep(5*1000);
            String path = SingletonRunner.class.getResource("/").getPath() + "serializableFile.txt";
            fis = new FileInputStream(new File(path));
            ois = new ObjectInputStream(fis);
            StaticInnerClassSingletonSeralizableOneInstance staticInnerClassSingletonSeralizableOneInstance = (StaticInnerClassSingletonSeralizableOneInstance)ois.readObject();
            System.out.println(staticInnerClassSingletonSeralizableOneInstance.hashCode());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void staticBlockSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + StaticBlockSingleton.getInstance().hashCode());
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();
    }

    public void enumSingletonTest() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -> " + EnumSingleton.getInstance().hashCode());
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }


}
