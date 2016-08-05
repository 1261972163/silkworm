package com.jengine.java.lang;

import org.junit.Test;

/**
 * @author nouuid
 * @date 2/29/2016
 * @description
 */
public class ShareDataTest {
    @Test
    public void loginTest() {
        ALogin a = new ALogin();
        a.start();
        BLogin b = new BLogin();
        b.start();

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginTest2() {
        ALogin2 a = new ALogin2();
        a.start();
        BLogin2 b = new BLogin2();
        b.start();

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ALogin extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost("a", "aa");
    }
}

class BLogin extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost("b", "bb");
    }
}

class ALogin2 extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost2("a", "aa");
    }
}

class BLogin2 extends Thread {
    @Override
    public void run() {
        LoginServlet.doPost2("b", "bb");
    }
}

class LoginServlet {
    private static String usernameRef;
    private static String passwordRef;
    public static void doPost(String username, String password) {
        try {
            usernameRef = username;
            if (username.equals("a")) {
                Thread.sleep(5000);
            }
            passwordRef = password;
            System.out.println("username=" + usernameRef + " password=" + password);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    synchronized public static void doPost2(String username, String password) {
        try {
            usernameRef = username;
            if (username.equals("a")) {
                Thread.sleep(5000);
            }
            passwordRef = password;
            System.out.println("username=" + usernameRef + " password=" + password);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}