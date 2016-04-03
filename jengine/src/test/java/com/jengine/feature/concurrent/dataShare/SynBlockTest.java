package com.jengine.feature.concurrent.dataShare;

import com.jengine.feature.concurrent.ConcurrentTest;
import com.jengine.feature.concurrent.dataShare.synBlock.*;
import com.jengine.feature.concurrent.dataShare.synMethod.SynStaticMethodRunner;
import org.junit.Test;

/**
 * @author bl07637
 * @date 3/31/2016
 * @description
 */
public class SynBlockTest extends ConcurrentTest {

    @Test
    public void synBlock()  throws InterruptedException {
        SynBlockRunner synBlockRunner = new SynBlockRunner();
        synBlockRunner.test();

        Thread.sleep(20*1000);
    }

    @Test
    public void halfSynAndAsyn() throws InterruptedException {
        HalfSynAndAsynRunner halfSynAndAsynRunner = new HalfSynAndAsynRunner();
        halfSynAndAsynRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synBlocksSynchronism() throws InterruptedException {
        SynBlocksSynchronismRunner synBlocksSynchronismRunner = new SynBlocksSynchronismRunner();
        synBlocksSynchronismRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synBlockObject() throws InterruptedException {
        SynBlockObjectRunner synBlockObjectRunner = new SynBlockObjectRunner();
        synBlockObjectRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synStaticMethod() throws InterruptedException {
        SynStaticMethodRunner synStaticMethodRunner = new SynStaticMethodRunner();
        synStaticMethodRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synClass() throws InterruptedException {
        SynClassRunner synClassRunner = new SynClassRunner();
        synClassRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synStringConstantPoolFeature() throws InterruptedException {
        SynStringConstantPoolFeatureRunner synStringConstantPoolFeatureRunner = new SynStringConstantPoolFeatureRunner();
        synStringConstantPoolFeatureRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void deadLock() throws InterruptedException {
        DeadLockRunner deadLockRunner = new DeadLockRunner();
        deadLockRunner.test();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void synStaticInnerClass() throws InterruptedException {
        SynStaticInnerClassRunner synStaticInnerClassRunner = new SynStaticInnerClassRunner();
        synStaticInnerClassRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synInnerClass() throws InterruptedException {
        SynInnerClassRunner synInnerClassRunner = new SynInnerClassRunner();
        synInnerClassRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synBlockObjectChanged() throws InterruptedException {
        SynBlockObjectChangedRunner synBlockObjectChangedRunner = new SynBlockObjectChangedRunner();
        synBlockObjectChangedRunner.test();
        Thread.sleep(10*1000);
    }
}
