package com.qewby.network.processor;

import java.util.logging.Logger;

import com.qewby.network.encryption.Encryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketBuilder;
import com.qewby.network.packet.PacketWriter;

public class EncryptedResponseBuilder implements ResponseBuilder {

    public EncryptedResponseBuilder() {
    }

    @Override
    public byte[] build(final Message response) {
        try {
            Logger.getGlobal().info("Response message: " + new String(response.getMessage()));

            Encryptor encryptor = new Encryptor();
            int cType = response.getCType();
            int bUserId = response.getBUserId();
            byte[] encryptedMessage = encryptor.encrypt(response.getMessage());
            Message encryptedResponse = new Message(cType, bUserId, encryptedMessage);

            Packet responsePacket = PacketBuilder.build(encryptedResponse);
            byte[] responseBytePacket = PacketWriter.write(responsePacket);

            return responseBytePacket;
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
            return new byte[1];
        }
    }
}
