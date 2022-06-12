package com.qewby.network;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Before;

public class PacketTest {

    private Packet p;

    private final byte bMagic = 0x13;
    private final byte bSrc = 0x01;
    private final long bPktId = 0x0000abcd;
    private final int wLen = 0x00000014;
    private final short wCrc16 = 0x7fff;
    private final int cType = 0x00000001;
    private final int bUserId = 0x00000001;
    private final String message = "Hello world!";
    private final Message bMsg = new Message(cType, bUserId, message.getBytes());
    private final short wMsgCrc16 = 0x7ff0;

    @Before
    public void testConstructor() {
        p = new Packet(
                bMagic,
                bSrc,
                bPktId,
                wLen,
                wCrc16,
                bMsg,
                wMsgCrc16);
    }

    @Test
    public void testBMagicGetter() {
        assertEquals(bMagic, p.getBMagic());
    }

    @Test
    public void testBSrcGetter() {
        assertEquals(bSrc, p.getBSrc());
    }

    @Test
    public void testBPktIdGetter() {
        assertEquals(bPktId, p.getBPktId());
    }

    @Test
    public void testWLenGetter() {
        assertEquals(wLen, p.getWLen());
    }

    @Test
    public void testWCrc16Getter() {
        assertEquals(wCrc16, p.getWCrc16());
    }

    @Test
    public void testWMsgCrc16() {
        assertEquals(wMsgCrc16, p.getWMsgCrc16());
    }

    @Test
    public void testBMsgSizeEqualsToWLen() {
        assertEquals(wLen, p.getBMsg().getMessage().length + Integer.BYTES * 2);
    }
}
