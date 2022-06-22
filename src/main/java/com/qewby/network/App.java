package com.qewby.network;

import com.qewby.network.io.FakeReceiver;

public class App {
    public static void main(String[] args) {
        Thread listener = new Thread(new FakeReceiver());
        listener.start();
    }

    public static String helloWorld() {
        return "Hello world";
    }
}
