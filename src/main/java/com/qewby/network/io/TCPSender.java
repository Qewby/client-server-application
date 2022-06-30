package com.qewby.network.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

import com.qewby.network.packet.Message;
import com.qewby.network.packet.PacketReader;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.PacketParser;

import lombok.extern.java.Log;

public class TCPSender implements Sender {

    private Socket clientSocket;
    private OutputStream out;
    private InputStream in;
    InetAddress target;

    public TCPSender(InetAddress target) {
        this.target = target;
    }

    public void startConnection(InetAddress target, int port) throws IOException {
        clientSocket = new Socket(target, port);
        out = clientSocket.getOutputStream();
        in = clientSocket.getInputStream();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    public void sendMessage(byte[] packet) {
        try {
            byte[] buffer = new byte[8096];

            startConnection(target, 1337);
            /*
             * try {
             * Thread.sleep(5000);
             * } catch (InterruptedException e) {
             * Logger.getGlobal().warning(e.getMessage());
             * }
             */

            out.write(packet);
            Logger.getGlobal().info("Sended request: " + new String(packet));
            if (in.read(buffer) != -1) {
                PacketParser parser = new EncryptedParser();
                Message response = parser.getRequestMessage(buffer);
                Logger.getGlobal().info("Response readed: " + new String(buffer));
                Logger.getGlobal().info("Message: " + new String(response.getMessage()));
            } else {
                Logger.getGlobal().info("Server error");
            }
            stopConnection();
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
