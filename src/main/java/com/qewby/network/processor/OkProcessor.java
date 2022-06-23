package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public class OkProcessor implements Processor {
    private Message message;

    public OkProcessor() {
        this.message = null;
    }

    public OkProcessor(Message message) {
        this.message = message;
    }

    @Override
    public void process(Message message) {
        String responseMessage = "OK";
        Message response = new Message(message.getCType(), message.getBUserId(), responseMessage.getBytes());

        ResponseBuilder builder = new EncryptedResponseBuilder(response);
        builder.run();
    }

    @Override
    public void run() {
        process(message);
    }
    
}
