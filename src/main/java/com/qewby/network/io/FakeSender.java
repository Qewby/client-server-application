package com.qewby.network.io;

import java.lang.String;

import java.net.InetAddress;
import java.util.logging.Logger;

public class FakeSender implements Sender {
    private static Logger logger = Logger.getGlobal();
    @Override
    public void sendMessage(byte[] packet, InetAddress target) {
        String packetString = new String(packet);
        logger.info("Sended message: " + packetString);
    }
}
