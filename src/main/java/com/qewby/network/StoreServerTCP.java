package com.qewby.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.qewby.network.io.TCPReceiver;
import com.qewby.network.processor.ThreadPool;

public class StoreServerTCP {

    private ServerSocket serverSocket;
    private boolean runned;

    public void start(int port) throws IOException {
        runned = true;
        serverSocket = new ServerSocket(port);
        while (runned) {
            ThreadPool.submitTask(new TCPReceiver(serverSocket.accept()), 10, TimeUnit.SECONDS);
            Logger.getGlobal().info("Accepted new user");
        }
    }

    public void stop() throws IOException {
        runned = false;
        serverSocket.close();
    }

    public static void main(String[] args) {
        StoreServerTCP server = new StoreServerTCP();
        try {
            server.start(1337);
        } catch (IOException e) {
            Logger.getGlobal().severe(e.getMessage());
        }
    }
}
