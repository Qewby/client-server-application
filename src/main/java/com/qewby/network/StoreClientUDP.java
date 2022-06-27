package com.qewby.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Logger;

import com.qewby.network.io.UDPSender;

public class StoreClientUDP {
    private static final int bufferSize = 8096;

    private DatagramSocket socket;
    private byte[] buf;

    public StoreClientUDP() throws SocketException {
        buf = new byte[bufferSize];
        socket = new DatagramSocket(0);
    }

    byte[] sendRequest(byte[] request) throws IOException {
        UDPSender sender = new UDPSender(socket, InetAddress.getByName(null), StoreServerUDP.port);
        sender.sendMessage(request);

        DatagramPacket response = new DatagramPacket(buf, buf.length);
        socket.receive(response);

        return Arrays.copyOfRange(buf, response.getOffset(), response.getOffset() + response.getLength());
    }

    public static void main(String[] args) {
        try {
            StoreClientUDP client = new StoreClientUDP();
            byte[] response = client.sendRequest("Hello".getBytes());
            Logger.getGlobal().info(new String(response));
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
