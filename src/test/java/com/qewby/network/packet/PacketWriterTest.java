package com.qewby.network.packet;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class PacketWriterTest {
    private byte bMagic;
    private byte bSrc;
    private long bPktId;
    private int wLen;
    private short wCrc16;
    private int cType;
    private int bUserId;
    private String message;
    private Message bMsg;
    private short wMsgCrc16;

    @Before
    public void setValidInitialPacketValues() {
        bMagic = 0x13;
        bSrc = 0x01;
        bPktId = 0x000000000000abcd;
        wLen = 0x00000014;
        ByteBuffer header = ByteBuffer.allocate(14);
        header.put(bMagic).put(bSrc).putLong(bPktId).putInt(wLen);
        wCrc16 = CRC16.calculate(header.array());

        cType = 0x00000001;
        bUserId = 0x00000001;
        message = "Hello world!";
        bMsg = new Message(cType, bUserId, message.getBytes());
        assertEquals(message.length() + Integer.BYTES * 2, wLen);
        ByteBuffer msg = ByteBuffer.allocate(wLen);
        msg.putInt(cType).putInt(bUserId).put(message.getBytes());
        wMsgCrc16 = CRC16.calculate(msg.array());
    }

    @Test
    public void testDefaultConstructor() {
        new PacketWriter();
    }

    @Test(expected = IllegalArgumentException.class)
    public void writePacketWithInvalidMagicNumber() {
        byte invalidMagic = Packet.MagicValue + 1;
        Packet packet = new Packet(invalidMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
        PacketWriter.write(packet);
    }

    @Test
    public void testWLenOfEncrypted() throws Exception {
        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
        byte[] result = PacketWriter.write(packet);
        ByteBuffer buffer = ByteBuffer.wrap(result);
        int wLenOffset = 10;
        int actual = buffer.getInt(wLenOffset);
        int expected = message.length() + Integer.BYTES * 2;
        assertEquals(expected, actual);
    }

    @Test
    public void messageShouldEqualToMessageFromObject() throws Exception {
        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
        byte[] result = PacketWriter.write(packet);
        int messageOffset = 24;
        int wLenOffset = 10;
        ByteBuffer buffer = ByteBuffer.wrap(result);
        int messageLength = buffer.getInt(wLenOffset) - Integer.BYTES * 2;
        String expected = message;
        String actual = new String(Arrays.copyOfRange(result, messageOffset, messageOffset + messageLength));
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHeaderCRC16() {
        short invalidWCrc16 = (short) (wCrc16 + 1);
        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, invalidWCrc16, bMsg, wMsgCrc16);
        PacketWriter.write(packet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMessageCRC16() {
        short invalidWMsgCrc16 = (short) (wCrc16 + 1);
        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, invalidWMsgCrc16);
        PacketWriter.write(packet);
    }
}
