package com.qewby.network.packet;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class PacketBuilderTest {
    private final Message message = new Message(1, 1, "Hello world".getBytes());

    @Test
    public void testDefaultConstructor() {
        new PacketBuilder();
    }

    @Test
    public void testWLenValueEqualsToMessageSize() {
        Packet actual = PacketBuilder.build(message);
        assertEquals(message.getMessage().length + Integer.BYTES * 2, actual.getWLen());
    }

    @Test
    public void testMagicNumberIsValid() {
        Packet actual = PacketBuilder.build(message);
        assertEquals(Packet.MagicValue, actual.getBMagic());
    }

    @Test
    public void testMessageInPackageEqualsToInitialMessage() {
        Packet actual = PacketBuilder.build(message);
        assertEquals(message.getCType(), actual.getBMsg().getCType());
        assertEquals(message.getBUserId(), actual.getBMsg().getBUserId());
        assertEquals(message.getMessage(), actual.getBMsg().getMessage());
    }

    @Test
    public void testMessageCRC16IsValid() {
        Packet packet = PacketBuilder.build(message);
        ByteBuffer msg = ByteBuffer.allocate(message.getMessage().length + Integer.BYTES * 2);
        msg.putInt(message.getCType()).putInt(message.getBUserId()).put(message.getMessage());
        short expected = CRC16.calculate(msg.array());
        short actual = packet.getWMsgCrc16();
        assertEquals(expected, actual);
    }
}
