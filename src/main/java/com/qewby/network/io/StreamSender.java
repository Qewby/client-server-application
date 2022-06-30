package com.qewby.network.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class StreamSender implements Sender {

    private OutputStream stream;

    public StreamSender(OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void sendMessage(byte[] packet) {
        try {
            stream.write(packet);
            stream.flush();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
