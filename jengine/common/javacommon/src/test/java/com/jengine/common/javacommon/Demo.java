package  com.jengine.common.javacommon;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by weiyang on 11/23/17.
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
//        demo.test1();
//        demo.test2();
        demo.test3();
        System.out.println("end.");

    }

    public void test1() {
//        Student student = new Student();
//        student.setName("1");
//        Map<String, Student> map = new HashMap<String, Student>();
//        map.put("1", student);
//        map.get("1").setName("2");
//        System.out.println(student.getName());
    }

    public void test2() throws InterruptedException {
//        final Student student = new Student();
//        CountDownLatch countDownLatch = new CountDownLatch(2);
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                student.setName("1");
//                System.out.println(Thread.currentThread().getName() + " -> " + student.getName());
//                countDownLatch.countDown();
//            }
//        }, "t1");
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                student.setName("2");
//                System.out.println(Thread.currentThread().getName() + " -> " + student.getName());
//                countDownLatch.countDown();
//            }
//        }, "t2");
//        thread1.start();
//        thread2.start();
//        countDownLatch.await();
    }

    public void test3() throws InterruptedException {
        final Student student = new Student("s");
        final Map<String, Student> map = new HashMap<String, Student>();
        map.put("1", student);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 5) {
                    Student student = map.get("1");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Student studentTmp = new Student(student.getName() + "1");
//                    synchronized (student) {
//                        student.setName("1");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.out.println(Thread.currentThread().getName() + " -> " + student.getName());
                        System.out.println(Thread.currentThread().getName() + " -> " + studentTmp.getName());
//                    }
                    i++;
                }
                countDownLatch.countDown();
            }
        }, "t1");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 5) {
                    Student student = map.get("1");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Student studentTmp = new Student(student.getName() + "2");
//                    synchronized (student) {
//                        student.setName("2");
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        System.out.println(Thread.currentThread().getName() + " -> " + student.getName());
                        System.out.println(Thread.currentThread().getName() + " -> " + studentTmp.getName());
//                    }
                    i++;
                }
                countDownLatch.countDown();
            }
        }, "t2");
        thread1.start();
        thread2.start();
        countDownLatch.await();
    }

    class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

//        public void setName(String name) {
//            this.name = name;
//        }

    }
}
