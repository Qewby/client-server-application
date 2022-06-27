package com.qewby.network.io;

import java.lang.String;

import java.util.logging.Logger;

public class FakeSender implements Sender {
    private static Logger logger = Logger.getGlobal();

    public FakeSender() {
    }

    @Override
    public void sendMessage(byte[] packet) {
        String packetString = new String(packet);
        logger.info("Sended message: " + packetString);
    }

}
