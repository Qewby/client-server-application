package com.qewby.network.io;

import java.util.concurrent.RejectedExecutionException;

import org.junit.Test;

import com.qewby.network.processor.ThreadPool;

public class FakeReceiverTest {

    @Test
    public void testPacketGenerating() {
        Receiver receiver = new FakeReceiver();
        receiver.receiveMessage();
    }

    @Test
    public void testPacketGeneratingInThread() throws InterruptedException {
        Receiver receiver = new FakeReceiver();
        Thread thread1 = new Thread(receiver);
        thread1.start();
        Thread thread2 = new Thread(receiver);
        thread2.start();
        receiver.run();

        Thread.sleep(2000);
    }

    @Test(expected = RejectedExecutionException.class)
    public void exceptionWhenThreadPoolClosed() {
        Receiver receiver = new FakeReceiver();
        ThreadPool.stopThreadPool();
        receiver.run();
    } 
}
