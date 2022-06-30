package com.qewby.network;

import java.net.InetAddress;

import com.qewby.network.io.Sender;
import com.qewby.network.io.TCPSender;
import com.qewby.network.packet.RequestPacketRandomizer;

public class StoreClientTCP {
    public static void main(String[] args) {
        try {
            Sender sender = new TCPSender(InetAddress.getByName("localhost"));
            sender.sendMessage(RequestPacketRandomizer.getPacket());
        } catch (Exception e) {
        }
    }
}
