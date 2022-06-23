package com.qewby.network.processor;

import java.util.logging.Logger;

import com.qewby.network.encryption.Encryptor;
import com.qewby.network.io.FakeSender;
import com.qewby.network.io.Sender;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketBuilder;
import com.qewby.network.packet.PacketWriter;

public class EncryptedResponseBuilder implements ResponseBuilder {
    private static Logger logger = Logger.getGlobal();

    private Message response;

    EncryptedResponseBuilder(Message response) {
        this.response = response;
    }

    @Override
    public void run() {
        build(response);
    }

    @Override
    public void build(Message response) {
        try {
            Encryptor encryptor = new Encryptor();

            logger.info("Response message: " + new String(response.getMessage()));
            int cType = response.getCType();
            int bUserId = response.getBUserId();
            byte[] encryptedMessage = encryptor.encrypt(response.getMessage());
            Message encryptedResponse = new Message(cType, bUserId, encryptedMessage);

            Packet responsePacket = PacketBuilder.build(encryptedResponse);
            byte[] responseBytePacket = PacketWriter.write(responsePacket);

            Sender sender = new FakeSender(responseBytePacket);
            sender.run();
            
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}
