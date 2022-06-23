package com.qewby.network.processor;

import java.util.logging.Logger;

import com.qewby.network.encryption.Decryptor;
import com.qewby.network.packet.Message;
import com.qewby.network.packet.Packet;
import com.qewby.network.packet.PacketReader;

public class BytePacketHandler implements Runnable {
    private static Logger logger = Logger.getGlobal();

    private byte[] requestBytePacket;

    public BytePacketHandler(byte[] request) {
        requestBytePacket = request;
    }

    private Message getRequestMessage() {

        try {
            Packet requestPacket = PacketReader.read(requestBytePacket);
            int cType = requestPacket.getBMsg().getCType();
            int bUserId = requestPacket.getBMsg().getBUserId();
            byte[] encryptedMessage = requestPacket.getBMsg().getMessage();

            Decryptor decryptor = new Decryptor();
            byte[] decryptedMessage = decryptor.decrypt(encryptedMessage);
            Message request = new Message(cType, bUserId, decryptedMessage);
            logger.info("Decrypted message: " + new String(request.getMessage()));

            return request;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return null;
        }
    }

    @Override
    public void run() {
        Message request = getRequestMessage();

        Processor processor = new OkProcessor(request);
        processor.run();
    }
}
