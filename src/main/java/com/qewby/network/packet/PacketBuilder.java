package com.qewby.network.packet;

import java.nio.ByteBuffer;

public class PacketBuilder {
    public static Packet build(Message message) {
        byte bMagic = Packet.MagicValue;
        byte bSrc = 111;
        long bPktId = 1;
        int wLen = message.getMessage().length + Integer.BYTES * 2;
        ByteBuffer header = ByteBuffer.allocate(14);
        header.put(bMagic).put(bSrc).putLong(bPktId).putInt(wLen);
        short wCrc16 = CRC16.calculate(header.array());

        ByteBuffer msg = ByteBuffer.allocate(wLen);
        msg.putInt(message.getCType()).putInt(message.getBUserId()).put(message.getMessage());
        short wMsgCrc16 = CRC16.calculate(msg.array());
        Packet result = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, message, wMsgCrc16);
        return result;
    }
}
