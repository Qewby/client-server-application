package com.qewby.network;

import java.nio.ByteBuffer;

import com.qewby.network.encryption.Encryptor;

public class PacketWriter {

    public static byte[] write(Packet packet) {
        Packet encryptedPacket = encryptPacket(packet);
        if (encryptedPacket.getBMagic() != Packet.MagicValue) {
            throw new IllegalArgumentException(
                    "Magic value is not valid: " + encryptedPacket.getBMagic() + " but need " + Packet.MagicValue);
        }
        ByteBuffer byteStream = ByteBuffer.allocate(18 + encryptedPacket.getWLen());
        byteStream.put(encryptedPacket.getBMagic())
                .put(encryptedPacket.getBSrc())
                .putLong(encryptedPacket.getBPktId())
                .putInt(encryptedPacket.getWLen())
                .putShort(encryptedPacket.getWCrc16())
                .putInt(encryptedPacket.getBMsg().getCType())
                .putInt(encryptedPacket.getBMsg().getBUserId())
                .put(encryptedPacket.getBMsg().getMessage())
                .putShort(encryptedPacket.getWMsgCrc16());
        return byteStream.array();
    }

    private static Packet encryptPacket(Packet packet) {
        byte[] encryptedMessage = null;
        int lengthEncrypted = 0;
        try {
            encryptedMessage = Encryptor.encrypt(packet.getBMsg().getMessage());
            lengthEncrypted = encryptedMessage.length + Integer.BYTES * 2;
        } catch (Exception e) {
            throw new UnknownError("Cannot encrypt message: " + e.getMessage());
        }
        final byte bMagic = packet.getBMagic();
        final byte bSrc = packet.getBSrc();
        final long bPktId = packet.getBPktId();
        final int wLen = lengthEncrypted;
        ByteBuffer header = ByteBuffer.allocate(14);
        header.put(bMagic).put(bSrc).putLong(bPktId).putInt(wLen);
        final short wCrc16 = CRC16.calculate(header.array());

        final int cType = packet.getBMsg().getCType();
        final int bUserId = packet.getBMsg().getBUserId();
        ByteBuffer msg = ByteBuffer.allocate(lengthEncrypted);
        msg.putInt(cType).putInt(bUserId).put(encryptedMessage);
        final short wMsgCrc16 = CRC16.calculate(msg.array());

        final Message bMsg = new Message(cType, bUserId, encryptedMessage);
        return new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
    }
}
