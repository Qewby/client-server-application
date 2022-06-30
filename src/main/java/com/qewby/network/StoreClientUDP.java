package com.qewby.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Logger;

import com.qewby.network.io.UDPSender;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.RequestPacketRandomizer;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.PacketParser;

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
        socket.setSoTimeout(5000);
        socket.receive(response);

        return Arrays.copyOfRange(buf, response.getOffset(), response.getOffset() + response.getLength());
    }

    public static void main(String[] args) {
        boolean received = false;
        while (!received) {
            try {
                StoreClientUDP client = new StoreClientUDP();
                byte[] buffer = client.sendRequest(RequestPacketRandomizer.getPacket());
                PacketParser parser = new EncryptedParser();
                Message response = parser.getRequestMessage(buffer);
                Logger.getGlobal().info("Response readed: " + new String(buffer));
                Logger.getGlobal().info("Message: " + new String(response.getMessage()));
                received = true;
            } catch (Exception e) {
                Logger.getGlobal().severe(e.getMessage());
            }
        }
    }
}
