package com.qewby.network.processor;

import java.util.logging.Logger;

import com.qewby.network.encryption.Decryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketReader;

public class EncryptedRequestParser implements RequestParser {

    public EncryptedRequestParser() {
    }

    public Message getRequestMessage(final byte[] requestBytePacket) {
        try {
            Packet requestPacket = PacketReader.read(requestBytePacket);
            int cType = requestPacket.getBMsg().getCType();
            int bUserId = requestPacket.getBMsg().getBUserId();
            byte[] encryptedMessage = requestPacket.getBMsg().getMessage();

            Decryptor decryptor = new Decryptor();
            byte[] decryptedMessage = decryptor.decrypt(encryptedMessage);
            Message request = new Message(cType, bUserId, decryptedMessage);
            Logger.getGlobal().info("Decrypted message: " + new String(request.getMessage()));

            return request;
        } catch (Exception e) {
            Logger.getGlobal().severe(e.getMessage());
            return null;
        }
    }
}
