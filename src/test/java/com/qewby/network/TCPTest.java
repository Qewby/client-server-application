package com.qewby.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qewby.network.io.Sender;
import com.qewby.network.io.TCPSender;
import com.qewby.network.packet.RequestPacketRandomizer;

public class TCPTest {

    private static StoreServerTCP server;

    @BeforeClass
    public static void runServer() throws IOException {
        new Thread(() -> {
            server = new StoreServerTCP();
            try {
                server.start(1337);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Test
    public void testSendingRequestAndReceiveResponse() throws UnknownHostException {
        TCPSender sender = new TCPSender(InetAddress.getByName("localhost"));
        String actual = sender.sendRequest(RequestPacketRandomizer.getPacket());
        String expected = "OK";
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void stopServer() throws IOException {
        server.stop();
    }
}
