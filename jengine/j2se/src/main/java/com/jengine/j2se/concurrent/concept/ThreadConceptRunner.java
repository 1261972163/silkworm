package com.jengine.j2se.concurrent.concept;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class ThreadConceptRunner {

    public void threadRunTest() {
        ThreadConcept threadConcept = new ThreadConcept();
        threadConcept.start();
    }

    public void threadTaskRunTest() {
        Thread thread = new Thread(new ThreadRunnableConcept());
        thread.start();
    }

    public void threadAnonymityTest() {
        ThreadTaskConcept threadTaskConcept = new ThreadTaskConcept();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                threadTaskConcept.service();
            }
        });
        thread.start();
    }
}

class ThreadConcept extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("ThreadConcept do");
    }
}

class ThreadRunnableConcept implements Runnable {

    @Override
    public void run() {
        System.out.println("ThreadRunnableConcept do");
    }
}

class ThreadTaskConcept {

    public void service() {
        System.out.println("ThreadTaskConcept do");
    }
}
