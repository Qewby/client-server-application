package com.qewby.network;

import com.qewby.network.io.FakeReceiver;
import com.qewby.network.io.Receiver;
import com.qewby.network.processor.ThreadPool;

public class App {
    public static void main(String[] args) {
        Receiver receiver = new FakeReceiver();
        Thread listener1 = new Thread(receiver);
        Thread listener2 = new Thread(receiver);
        listener1.start();
        listener2.start();

        try {
            listener1.join();
            listener2.join();
            ThreadPool.stopThreadPool();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String helloWorld() {
        return "Hello world";
    }
}
