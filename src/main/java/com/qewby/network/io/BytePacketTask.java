package com.qewby.network.io;

import java.util.logging.Logger;

import com.qewby.network.encryption.Decryptor;
import com.qewby.network.encryption.Encryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketBuilder;
import com.qewby.network.packet.PacketReader;
import com.qewby.network.packet.PacketWriter;

public class BytePacketTask implements Runnable {
    private static Logger logger = Logger.getGlobal();

    private byte[] requestBytePacket;
    private Packet requestPacket;

    public BytePacketTask(byte[] request) {
        requestBytePacket = request;
        requestPacket = null;
    }

    private Message getRequestMessage() {

        try {
            Decryptor decryptor = new Decryptor();
            requestPacket = PacketReader.read(requestBytePacket);
            int cType = requestPacket.getBMsg().getCType();
            int bUserId = requestPacket.getBMsg().getBUserId();
            byte[] encryptedMessage = requestPacket.getBMsg().getMessage();

            byte[] decryptedRequestMessage = decryptor.decrypt(encryptedMessage);
            Message request = new Message(cType, bUserId, decryptedRequestMessage);
            logger.info("Decrypted message: " + new String(request.getMessage()));

            return request;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    private void sendResponse(String responseMessage) {
        try {
            Encryptor encryptor = new Encryptor();
            int cType = requestPacket.getBMsg().getCType();
            int bUserId = requestPacket.getBMsg().getBUserId();

            byte[] encryptedMessage = encryptor.encrypt(responseMessage.getBytes());
            logger.info("Response message: " + responseMessage);
            Message response = new Message(cType, bUserId, encryptedMessage);

            Packet responsePacket = PacketBuilder.build(response);
            byte[] responseBytePacket = PacketWriter.write(responsePacket);
            
            Sender sender = new FakeSender();
            sender.sendMessage(responseBytePacket, null);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void run() {
        Message request = getRequestMessage();



        String responseMessage = "OK";
        sendResponse(responseMessage);
    }
}
