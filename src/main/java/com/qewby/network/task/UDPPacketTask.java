package com.qewby.network.task;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.qewby.network.io.Sender;
import com.qewby.network.io.UDPSender;
import com.qewby.network.packet.Message;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.EncryptedResponseBuilder;
import com.qewby.network.processor.OkProcessor;
import com.qewby.network.processor.PacketParser;
import com.qewby.network.processor.Processor;
import com.qewby.network.processor.ResponseBuilder;

public class UDPPacketTask implements Runnable {
    private static final PacketParser parser = new EncryptedParser();
    private static final Processor processor = new OkProcessor();
    private static final ResponseBuilder builder = new EncryptedResponseBuilder();

    DatagramSocket socket;
    private Sender sender;
    private byte[] request;

    public UDPPacketTask(DatagramSocket socket, DatagramPacket packet, byte[] request) {
        this.socket = socket;
        this.request = request;
        this.sender = new UDPSender(socket, packet);
    }

    @Override
    public void run() {
        Message response = null;
        try {
            Message requestObject = parser.getRequestMessage(request);
            response = processor.process(requestObject);
        } catch (Exception e) {
            response = new Message(0, 0, "Invalid packet".getBytes());
        }
        byte[] responsePacket = builder.build(response);
        sender.sendMessage(responsePacket);
    }
}
