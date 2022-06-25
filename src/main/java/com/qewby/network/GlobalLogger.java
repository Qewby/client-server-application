package com.qewby.network;

import java.util.logging.Logger;

public class GlobalLogger {
    private static Logger logger = Logger.getGlobal();

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warning(String msg) {
        logger.warning(msg);
    }

    public static void severe(String msg) {
        logger.severe(msg);
    }

    public static void fine(String msg) {
        logger.fine(msg);
    }
}
