package com.qewby.network;

import static org.junit.Assert.assertEquals;

import java.net.SocketException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UDPTest {
    private static StoreServerUDP server;

    @BeforeClass
    public static void runServer() throws SocketException {
        server = new StoreServerUDP();
        new Thread(server).start();
    }

    @Test
    public void testSendingRequestAndReceiveResponse() throws SocketException {
        StoreClientUDP client = new StoreClientUDP();
        String actual = client.send();
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void stopServer() {
        server.stop();
    }
}
