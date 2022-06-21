package com.qewby.network.encryption;

import java.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class Decryptor {
    private Cipher cipher;

    public Decryptor() throws UnknownError {
        Key key = KeyManager.getKey();
        try {
            cipher = Cipher.getInstance(KeyManager.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            throw new UnknownError(e.getMessage());
        }
    }

    synchronized public byte[] decrypt(byte[] message) throws BadPaddingException, IllegalBlockSizeException {
        byte[] decrypted = cipher.doFinal(message);
        return decrypted;
    }
}
