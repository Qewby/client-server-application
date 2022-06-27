package com.qewby.network.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class UDPSender implements Sender {
    DatagramSocket socket;
    private InetAddress target;
    private int port;

    public UDPSender(DatagramSocket socket, InetAddress address, int port) {
        this.socket = socket;
        this.target = address;
        this.port = port;
    }

    public UDPSender(DatagramSocket socket, DatagramPacket inputPacket) {
        this.socket = socket;
        this.target = inputPacket.getAddress();
        this.port = inputPacket.getPort();
    }

    @Override
    public void sendMessage(byte[] packet) {

        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, target, port);
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }

}
