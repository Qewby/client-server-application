package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface PacketParser {
    public Message getRequestMessage(final byte[] requestBytePacket);
}
