package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public class OkProcessor implements Processor {

    public OkProcessor() {
    }

    @Override
    public Message process(final Message message) {
        String responseMessage = "OK";
        Message response = new Message(message.getCType(), message.getBUserId(), responseMessage.getBytes());
        return response;
    }
}
