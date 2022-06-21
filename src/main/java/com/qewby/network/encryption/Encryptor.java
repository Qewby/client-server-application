package com.qewby.network.encryption;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encryptor {
    private static Cipher cipher = null;

    public static byte[] encrypt(byte[] message)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (cipher == null) {
            KeyManager manager = new KeyManager();
            Key key = manager.getKey();
            try {
                cipher = Cipher.getInstance(KeyManager.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } catch (NoSuchPaddingException e) {

            }
        }
        byte[] encrypted = cipher.doFinal(message);
        return encrypted;
    }
}
