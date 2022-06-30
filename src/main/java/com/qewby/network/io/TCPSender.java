package com.qewby.network.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

import com.qewby.network.packet.Message;
import com.qewby.network.processor.EncryptedParser;
import com.qewby.network.processor.PacketParser;

public class TCPSender implements Sender {

    private static String ERROR_MESSAGE = "Server error";

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

    public String sendRequest(byte[] request) {
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

            out.write(request);
            Logger.getGlobal().info("Sended request: " + new String(request));
            Message response = null;
            if (in.read(buffer) != -1) {
                PacketParser parser = new EncryptedParser();
                response = parser.getRequestMessage(buffer);
                Logger.getGlobal().info("Response readed: " + new String(buffer));
                Logger.getGlobal().info("Message: " + new String(response.getMessage()));
            } else {
                Logger.getGlobal().info(ERROR_MESSAGE);
                return ERROR_MESSAGE;
            }
            stopConnection();
            return new String(response.getMessage());
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public void sendMessage(byte[] packet) {
        sendRequest(packet);
    }
}
