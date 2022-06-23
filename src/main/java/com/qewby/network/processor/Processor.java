package com.qewby.network.processor;

import com.qewby.network.packet.Message;

public interface Processor extends Runnable {
    void process(Message message);
}
