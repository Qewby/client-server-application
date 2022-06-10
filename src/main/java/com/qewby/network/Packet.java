package com.qewby.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Packet {
    private byte bMagic = 0x13;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short wCrc16;
    private Message bMsg;
    private short WMsgCrc16;
}
