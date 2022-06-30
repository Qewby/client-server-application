package com.qewby.network.task;

import com.qewby.network.io.FakeSender;
import com.qewby.network.io.Sender;
import com.qewby.network.packet.Message;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.EncryptedResponseBuilder;
import com.qewby.network.processor.OkProcessor;
import com.qewby.network.processor.PacketParser;
import com.qewby.network.processor.Processor;
import com.qewby.network.processor.ResponseBuilder;

public class FakePacketTask implements Runnable {
    private static final PacketParser parser = new EncryptedParser();
    private static final Processor processor = new OkProcessor();
    private static final ResponseBuilder builder = new EncryptedResponseBuilder();
    private static final Sender sender = new FakeSender();
    private byte[] packet;
    
    public FakePacketTask(final byte[] packet) {
        this.packet = packet;
    }

    @Override
    public void run() {
        Message request = parser.getRequestMessage(packet);
        Message response = processor.process(request);
        byte[] responsePacket = builder.build(response);
        sender.sendMessage(responsePacket);
    }
}
