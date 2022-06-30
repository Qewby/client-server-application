package com.qewby.network.io;

import java.util.logging.Logger;

import com.qewby.network.packet.RequestPacketRandomizer;
import com.qewby.network.processor.ThreadPool;
import com.qewby.network.task.FakePacketTask;

public class FakeReceiver implements Receiver {

    @Override
    public void receiveMessage() {
        for (int i = 0; i < 100; ++i) {
            byte[] request = RequestPacketRandomizer.getPacket();
            Logger.getGlobal().info("Received packet: " + new String(request));

            ThreadPool.submitTask(new FakePacketTask(request));

            /*
             * try {
             * Thread.sleep(100);
             * } catch (InterruptedException e) {
             * logger.warning(e.getMessage());
             * }
             */
        }
    }

    @Override
    public void run() {
        receiveMessage();
    }
}
