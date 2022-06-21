package com.qewby.network.encryption;

import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Encryptor {
    private Cipher cipher;

    public Encryptor() throws UnknownError {
        Key key = KeyManager.getKey();
        try {
            cipher = Cipher.getInstance(KeyManager.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (Exception e) {
            throw new UnknownError(e.getMessage());
        }
    }

    synchronized public byte[] encrypt(byte[] message) throws BadPaddingException, IllegalBlockSizeException {
        byte[] encrypted = cipher.doFinal(message);
        return encrypted;
    }
}
