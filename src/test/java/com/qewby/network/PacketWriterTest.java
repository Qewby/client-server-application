package com.qewby.network;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Test;

import com.qewby.network.encryption.MessageEncryptor;

public class PacketWriterTest {
    private final byte bMagic = 0x13;
    private final byte bSrc = 0x01;
    private final long bPktId = 0x0000abcd;
    private final int wLen = 0x00000014; // not important
    private final short wCrc16 = 0x7fff; // not important
    private final int cType = 0x00000001;
    private final int bUserId = 0x00000001;
    private final String message = "Hello world!";
    private final Message bMsg = new Message(cType, bUserId, message.getBytes());
    private final short wMsgCrc16 = 0x7ff0; // not important

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
        int expected = MessageEncryptor.encrypt(message.getBytes()).length + Integer.BYTES * 2;
        assertEquals(expected, actual);
    }

    @Test
    public void messageShouldBeEncrypted() throws Exception {
        Packet packet = new Packet(bMagic, bSrc, bPktId, wLen, wCrc16, bMsg, wMsgCrc16);
        byte[] result = PacketWriter.write(packet);
        int messageOffset = 24;
        int wLenOffset = 10;
        ByteBuffer buffer = ByteBuffer.wrap(result);
        int messageLength = buffer.getInt(wLenOffset) - Integer.BYTES * 2;
        String expected = new String(MessageEncryptor.encrypt(message.getBytes()));
        String actual = new String(Arrays.copyOfRange(result, messageOffset, messageOffset + messageLength));
        assertEquals(expected, actual);
    }
}
