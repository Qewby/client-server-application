package com.qewby.network.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import com.qewby.network.GlobalLogger;

public class TCPReceiver implements Receiver {
    private static int bufferSize = 8096;

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
            try (DataInputStream in = new DataInputStream(clientSocket.getInputStream())) {
                ByteBuffer packet = ByteBuffer.allocate(bufferSize);
                packet.put(in.readByte()); // bMagic
                packet.put(in.readByte()); // bSrc
                packet.putLong(in.readLong()); // bPktId
                int expectedLength = in.readInt();
                packet.putInt(expectedLength);

                byte[] buffer = new byte[expectedLength];
                in.readFully(buffer);
                packet.put(buffer);

                out.write("Hello world".getBytes());
                out.flush();
            }
        } catch (IOException e) {
            GlobalLogger.severe(e.getMessage());
        }
    }
}
