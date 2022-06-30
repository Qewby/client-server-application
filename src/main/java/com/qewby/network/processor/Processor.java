package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface Processor {
    public Message process(final Message message);
}
