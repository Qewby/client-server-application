package com.qewby.network;

import java.nio.ByteBuffer;

public class PacketWriter {

    public static byte[] write(Packet packet) throws IllegalArgumentException {
        if (packet.getBMagic() != Packet.MagicValue) {
            throw new IllegalArgumentException(
                    "Magic value is not valid: " + packet.getBMagic() + " but need " + Packet.MagicValue);
        }

        ByteBuffer header = ByteBuffer.allocate(14);
        header.put(packet.getBMagic()).put(packet.getBSrc()).putLong(packet.getBPktId()).putInt(packet.getWLen());
        short expectedWCrc16 = CRC16.calculate(header.array());
        if (expectedWCrc16 != packet.getWCrc16()) {
            throw new IllegalArgumentException("Packet header CRC16 not valid");
        }

        ByteBuffer msg = ByteBuffer.allocate(packet.getWLen());
        msg.putInt(packet.getBMsg().getCType()).putInt(packet.getBMsg().getBUserId())
                .put(packet.getBMsg().getMessage());
        short expectedWMsgCrc16 = CRC16.calculate(msg.array());
        if (expectedWMsgCrc16 != packet.getWMsgCrc16()) {
            throw new IllegalArgumentException("Packet message CRC16 not valid");
        }

        ByteBuffer byteStream = ByteBuffer.allocate(18 + packet.getWLen());
        byteStream.put(packet.getBMagic())
                .put(packet.getBSrc())
                .putLong(packet.getBPktId())
                .putInt(packet.getWLen())
                .putShort(packet.getWCrc16())
                .putInt(packet.getBMsg().getCType())
                .putInt(packet.getBMsg().getBUserId())
                .put(packet.getBMsg().getMessage())
                .putShort(packet.getWMsgCrc16());
        return byteStream.array();
    }
}
