package com.qewby.network.io;

import java.net.InetAddress;

public interface Sender {
    public void sendMessage(byte[] packet, InetAddress target);
}
