package com.jengine.feature.concurrent.dataShare;

import com.jengine.feature.concurrent.ConcurrentTest;
import com.jengine.feature.concurrent.dataShare.synMethod.DataShareCounterSafeRunner;
import com.jengine.feature.concurrent.dataShare.synMethod.SynAllMethodRunner;
import com.jengine.feature.concurrent.dataShare.synMethod.SynLockInRunner;
import com.jengine.feature.concurrent.dataShare.synMethod.SynOneMethodRunner;
import org.junit.Test;

/**
 * @author bl07637
 * @date 3/31/2016
 * @description
 */
public class SynMethodTest extends ConcurrentTest {

    @Test
    public void noDataShareCounterRunnerTest() throws InterruptedException {
        NoDataShareCounterRunner noDataShareCounterRunner = new NoDataShareCounterRunner();
        noDataShareCounterRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void dataShareCounterSafeRunnerTest() throws InterruptedException {
        DataShareCounterSafeRunner dataShareCounterSafeRunner = new DataShareCounterSafeRunner();
        dataShareCounterSafeRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void dataShareCounterUnsafeRunnerTest() throws InterruptedException {
        DataShareCounterUnsafeRunner dataShareCounterUnsafeRunner = new DataShareCounterUnsafeRunner();
        dataShareCounterUnsafeRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void synOneMethod() throws InterruptedException {
        SynOneMethodRunner synOneMethodRunner = new SynOneMethodRunner();
        synOneMethodRunner.oneMethodSynchronized();
        Thread.sleep(10*1000);
    }

    @Test
    public void synAllMethod() throws InterruptedException {
        SynAllMethodRunner synAllMethodRunner = new SynAllMethodRunner();
        synAllMethodRunner.allMethodSynchronized();
        Thread.sleep(10*1000);
    }

    @Test
    public void synLockIn() throws InterruptedException {
        SynLockInRunner synLockInRunner = new SynLockInRunner();
        synLockInRunner.lockIn();
        Thread.sleep(10*1000);
    }
}
