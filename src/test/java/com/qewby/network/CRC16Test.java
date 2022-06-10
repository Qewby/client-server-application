package com.qewby.network;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CRC16Test {
    @Test
    public void testAbc() {
        short crc = CRC16.calculate("abc".getBytes());
        assertEquals((short) 0x9738, crc);
    }

    @Test
    public void testHello() {
        short crc = CRC16.calculate("hello".getBytes());
        assertEquals((short) 0x34d2, crc);
    }

    @Test
    public void testWorld() {
        short crc = CRC16.calculate("world".getBytes());
        assertEquals((short) 0xef65, crc);
    }

    @Test
    public void test123() {
        short crc = CRC16.calculate("123".getBytes());
        assertEquals((short) 0xba04, crc);
    }

}
