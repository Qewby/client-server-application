package com.qewby.network.packet;

import static org.junit.Assert.assertEquals;

import java.nio.BufferUnderflowException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class PacketReaderTest {
    private byte[] validPacket;

    @Test
    public void testDefaultConstructor() {
        new PacketReader();
    }

    @Before
    public void createPacketByteArray() {
        validPacket = new byte[] { // valid not encrypted packet (so cannot be decrypted)
                0x13,
                0x01,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0f,
                0x00, 0x00, 0x00, 0x0A,
                (byte) 0x54, (byte) 0xC4, // CRC16
                0x00, 0x00, 0x00, 0x01,
                0x00, 0x00, 0x00, 0x01,
                (byte) 'O', (byte) 'K',
                0x07, 0x35 // CRC16
        };
    }

    @Test
    public void testHeaderCRC16() {
        short expected = (short) 0x54C4;
        short actual = CRC16.calculate(Arrays.copyOfRange(validPacket, 0, 14));
        assertEquals(expected, actual);
    }

    @Test
    public void testMessageCRC16() {
        short expected = (short) 0x0735;
        short actual = CRC16.calculate(Arrays.copyOfRange(validPacket, 16, 26));
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMagicNumber() {

        byte[] packet = validPacket.clone();
        packet[0] = 0;
        PacketReader.read(packet);
    }

    @Test(expected = BufferUnderflowException.class)
    public void testBufferUnderflow() {
        byte[] packet = Arrays.copyOf(validPacket, 10);
        PacketReader.read(packet);
    }

    @Test(expected = BufferUnderflowException.class)
    public void testValidHeaderCRC16() {
        byte[] packet = Arrays.copyOf(validPacket, 17);
        PacketReader.read(packet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHeaderCRC16() {
        byte[] packet = Arrays.copyOf(validPacket, 17);
        ++(packet[14]);
        PacketReader.read(packet);
    }

    @Test(expected = BufferUnderflowException.class)
    public void testShorterMessageLength() {
        byte[] packet = Arrays.copyOf(validPacket, validPacket.length - 1);
        PacketReader.read(packet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongerMessageLength() {
        byte[] packet = new byte[validPacket.length + 1];
        System.arraycopy(validPacket, 0, packet, 0, validPacket.length);
        packet[packet.length - 1] = packet[packet.length - 2]; // CRC part
        packet[packet.length - 2] = packet[packet.length - 3]; // CRC part
        packet[packet.length - 3] = (byte) '!';
        PacketReader.read(packet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMessageCRC16() {
        byte[] packet = validPacket.clone();
        ++(packet[packet.length - 1]);
        PacketReader.read(packet);
    }

    @Test
    public void testNotEncryptedPacket() {
        byte[] packet = validPacket.clone();
        PacketReader.read(packet);
    }
}
