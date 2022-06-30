package com.qewby.network.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.qewby.network.packet.Message;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.EncryptedResponseBuilder;
import com.qewby.network.processor.OkProcessor;
import com.qewby.network.processor.PacketParser;
import com.qewby.network.processor.Processor;
import com.qewby.network.processor.ResponseBuilder;

public class TCPReceiver implements Receiver {
    private static final int bufferSize = 8096;
    private static final PacketParser parser = new EncryptedParser();
    private static final Processor processor = new OkProcessor();
    private static final ResponseBuilder builder = new EncryptedResponseBuilder();

    private Socket clientSocket;

    public TCPReceiver(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        receiveMessage();
    }

    @Override
    public void receiveMessage() {
        try (OutputStream out = clientSocket.getOutputStream()) {
            Sender sender = new StreamSender(out);
            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                ByteBuffer packet = ByteBuffer.allocate(bufferSize);
                packet.put(in.readByte()); // bMagic
                packet.put(in.readByte()); // bSrc
                packet.putLong(in.readLong()); // bPktId
                int expectedLength = in.readInt();
                packet.putInt(expectedLength);
                expectedLength += Short.BYTES * 2;

                byte[] buffer = new byte[expectedLength];
                in.readFully(buffer);
                packet.put(buffer);

                Message response = null;
                try {
                    Message request = parser.getRequestMessage(packet.array());
                    response = processor.process(request);
                } catch (Exception e) {
                    response = new Message(0, 0, "Invalid packet".getBytes());
                }
                byte[] responsePacket = builder.build(response);

                sender.sendMessage(responsePacket);
            }
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
