package com.qewby.network;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class PacketReader {

    public static Packet read(final byte[] packet) throws IllegalArgumentException {
        ByteBuffer buffer = ByteBuffer.wrap(packet);
        int startOffset = 0;

        final byte bMagic = buffer.get();
        if (bMagic != Packet.MagicValue) {
            throw new IllegalArgumentException("Bad magic byte value");
        }
        final byte bSrc = buffer.get();
        final long bPktId = buffer.getLong();
        final int wLen = buffer.getInt();
        final short wCrc16 = buffer.getShort();
        short expectedCrc16 = CRC16
                .calculate(Arrays.copyOfRange(packet, startOffset, buffer.position() - Short.BYTES));
        if (wCrc16 != expectedCrc16) {
            throw new IllegalArgumentException("Invalid packet header: CRC16 not valid");
        }
        startOffset = buffer.position();

        final int cType = buffer.getInt();
        final int bUserId = buffer.getInt();
        final byte[] message = new byte[wLen - Integer.BYTES * 2];
        buffer.get(message, 0, wLen - Integer.BYTES * 2);
        final short wMsgCrc16 = buffer.getShort();
        expectedCrc16 = CRC16
                .calculate(Arrays.copyOfRange(packet, startOffset, buffer.position() - Short.BYTES));
        if (wMsgCrc16 != expectedCrc16) {
            throw new IllegalArgumentException("Invalid packet header: CRC16 not valid");
        }

        final Message bMsg = new Message(cType, bUserId, message);
        Packet packetObject = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
        return packetObject;
    }
}