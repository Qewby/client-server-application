package com.qewby.network;

import java.net.InetAddress;

import com.qewby.network.io.Sender;
import com.qewby.network.io.TCPSender;

public class StoreClientTCP {
    public static void main(String[] args) {
        try {
            Sender sender = new TCPSender();
            sender.sendMessage("hello".getBytes(), InetAddress.getByName("localhost"));
        } catch (Exception e) {
        }
    }
}
