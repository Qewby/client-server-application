package com.qewby.network.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {
    private int cType;
    private int bUserId;
    private byte[] message;
}
