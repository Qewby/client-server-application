package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface ResponseBuilder extends Runnable {
    public void build(Message message);
}
