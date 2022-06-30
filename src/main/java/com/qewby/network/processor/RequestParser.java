package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface RequestParser {
    public Message getRequestMessage(final byte[] requestBytePacket);
}
