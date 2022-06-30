package com.qewby.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

import com.qewby.network.io.UDPSender;

public class StoreServerUDP implements Runnable {
    public static final int port = 1337;
    private static final int bufferSize = 8096;

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf;

    public StoreServerUDP() throws SocketException {
        buf = new byte[bufferSize];
        socket = new DatagramSocket(port);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                Logger.getGlobal().info(new String(packet.getData(), packet.getOffset(), packet.getLength()));

                UDPSender sender = new UDPSender(socket, packet);
                sender.sendMessage("packet".getBytes());
            } catch (IOException e) {
                Logger.getGlobal().warning(e.getMessage());
            }
        }
        socket.close();
    }

    public static void main(String[] args) {
        Thread server;
        try {
            server = new Thread(new StoreServerUDP());
            server.start();
        } catch (SocketException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
