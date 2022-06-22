package com.qewby.network.io;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.qewby.network.encryption.Encryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketBuilder;
import com.qewby.network.packet.PacketWriter;

public class FakeReceiver implements Runnable, Receiver {
    private static Vector<byte[]> requests = new Vector<>(6);
    private static Logger logger = Logger.getGlobal();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    static {
        Encryptor encryptor = new Encryptor();
        try {
            Message[] messages = {
                    new Message(1, 1, encryptor.encrypt("".getBytes())),
                    new Message(2, 1, encryptor.encrypt("{\"id\": 1, \"amout\": 10}".getBytes())),
                    new Message(3, 1, encryptor.encrypt("{\"id\": 1, \"amout\": 15}".getBytes())),
                    new Message(4, 1, encryptor.encrypt("{\"name\": \"овочі\"}".getBytes())),
                    new Message(5, 1, encryptor.encrypt("{\"product_id\": 1, \"group_id\": 1}".getBytes())),
                    new Message(6, 1, encryptor.encrypt("{\"product_id\": 1, \"price\": 100}".getBytes())),
            };

            for (Message encryptedMessage : messages) {
                Packet packet = PacketBuilder.build(encryptedMessage);
                byte[] bytePacket = PacketWriter.write(packet);
                //logger.info(new String(bytePacket));
                requests.add(bytePacket);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void receiveMessage() {
        for (int i = 0; i < 50; ++i) {
            Random random = new Random();
            byte[] request = requests.get(random.nextInt(requests.size()));
            String requestString = new String(request);
            logger.info("Received packet: " + requestString);

            threadPool.submit(new BytePacketTask(request));

            /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warning(e.getMessage());
            } */
        }
        threadPool.shutdown();
    }

    @Override
    public void run() {
        receiveMessage();
    }
}
