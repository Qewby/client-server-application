package com.qewby.network.io;

import org.junit.Test;

public class FakeReceiverTest {

    @Test
    public void testPacketGenerating() {
        FakeReceiver receiver = new FakeReceiver();
        receiver.receiveMessage();
    }

    @Test
    public void testPacketGeneratingInThread() throws InterruptedException {
        FakeReceiver receiver = new FakeReceiver();
        Thread thread1 = new Thread(receiver);
        thread1.start();
        Thread thread2 = new Thread(receiver);
        thread2.start();
        receiver.run();
    }
}
