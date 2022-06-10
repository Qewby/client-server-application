package com.qewby.network;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class MessageTest {
    private Message p;

    private final int cType = 0x00000001;
    private final int bUserId = 0x000000ff;
    private final byte[] message = "Hello world".getBytes();

    @Before
    public void testConstructor() {
        p = new Message(cType, bUserId, message);
    }

    @Test
    public void testCTypeGetter() {
        assertEquals(cType, p.getCType());
    }

    @Test
    public void testBUserIdGetter() {
        assertEquals(bUserId, p.getBUserId());
    }

    @Test
    public void testMessageGetter() {
        assertEquals(message, p.getMessage());
    }
}
