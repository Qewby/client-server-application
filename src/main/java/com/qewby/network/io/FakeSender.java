package com.qewby.network.io;

import java.lang.String;

import java.net.InetAddress;

public class FakeSender implements Sender {

    @Override
    public void sendMessage(byte[] packet, InetAddress target) {
        String packetString = new String(packet);
        System.out.println(packetString);
    }
}
