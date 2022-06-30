package com.qewby.network.packet;

import java.util.Random;
import java.util.Vector;

import com.qewby.network.encryption.Encryptor;

public class RequestPacketRandomizer {
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

    public static final byte[] getPacket() {
        Random random = new Random();
        return requests.get(random.nextInt(requests.size()));
    }
}
