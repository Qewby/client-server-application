package com.qewby.network.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.qewby.network.GlobalLogger;

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
            for (int i = 0; i < 13; ++i) {
                out.write(0);
            }
            out.write(packet.length + 4);
            GlobalLogger.info(String.valueOf(packet.length + 4));
            out.write(0);
            out.write(0);
            out.write(packet);
            out.write(0);
            out.write(0);
            GlobalLogger.info("request sended");
            if (in.read(buffer) != -1) {
                GlobalLogger.info("response readed: " + new String(buffer));
            }
            stopConnection();
        } catch (IOException e) {
            GlobalLogger.severe(e.getMessage());
        }
    }
}
