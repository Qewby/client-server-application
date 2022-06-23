package com.qewby.network.io;

import java.lang.String;

import java.net.InetAddress;
import java.util.logging.Logger;

public class FakeSender implements Sender {
    private static Logger logger = Logger.getGlobal();

    private byte[] packet;

    public FakeSender() {
        this.packet = null;
    }

    public FakeSender(byte[] packet) {
        this.packet = packet;
    }

    @Override
    public void sendMessage(byte[] packet, InetAddress target) {
        String packetString = new String(packet);
        logger.info("Sended message: " + packetString);
    }

    @Override
    public void run() {
        sendMessage(packet, null);
    }
}
