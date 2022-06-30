package com.qewby.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.qewby.network.processor.ThreadPool;
import com.qewby.network.task.UDPPacketTask;

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

    public void stop() {
        running = false;
        socket.close();
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                byte[] request = Arrays.copyOfRange(packet.getData(), packet.getOffset(),
                        packet.getOffset() + packet.getLength());
                ThreadPool.submitTask(new UDPPacketTask(socket, packet, request), 10, TimeUnit.SECONDS);

            } catch (IOException e) {
                Logger.getGlobal().warning(e.getMessage());
            }
        }
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
