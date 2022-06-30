package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface ResponseBuilder {
    public byte[] build(final Message message);
}
