package com.qewby.network.io;

import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;

import com.qewby.network.encryption.Encryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketBuilder;
import com.qewby.network.packet.PacketWriter;
import com.qewby.network.processor.BytePacketHandler;
import com.qewby.network.processor.ThreadPool;

public class FakeReceiver implements Receiver {
    private static Vector<byte[]> requests = new Vector<>(6);

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
                // logger.info(new String(bytePacket));
                requests.add(bytePacket);
            }
        } catch (Exception e) {
        }
    }

    private static Logger logger = Logger.getGlobal();

    @Override
    public void receiveMessage() {
        for (int i = 0; i < 10; ++i) {
            Random random = new Random();
            byte[] request = requests.get(random.nextInt(requests.size()));
            logger.info("Received packet: " + new String(request));

            ThreadPool.submitTask(new BytePacketHandler(request));

            /*
             * try {
             * Thread.sleep(100);
             * } catch (InterruptedException e) {
             * logger.warning(e.getMessage());
             * }
             */
        }
    }

    @Override
    public void run() {
        receiveMessage();
    }
}
