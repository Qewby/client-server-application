package com.qewby.network.io;

import java.lang.String;

import java.util.logging.Logger;

public class FakeSender implements Sender {

    public FakeSender() {
    }

    @Override
    public void sendMessage(final byte[] packet) {
        String packetString = new String(packet);
        Logger.getGlobal().info("Sended message: " + packetString);
    }

}
