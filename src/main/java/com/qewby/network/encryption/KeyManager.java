package com.qewby.network.encryption;

import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class KeyManager {
    private static KeyStore keyStore = null;
    private static String messageKeyAlias = "messageKey";
    private static String keyStoreFilePath = "data/keystore.ks";
    private static String password = "";
    private static String algorithm = "AES";

    public static String getAlgorithm() {
        return algorithm;
    }

    KeyManager() throws KeyStoreException, NoSuchAlgorithmException, CertificateException {
        if (keyStore == null) {
            keyStore = KeyStore.getInstance("JCEKS");
            try (FileInputStream keyStoreData = new FileInputStream(keyStoreFilePath)) {
                keyStore.load(keyStoreData, password.toCharArray());
            } catch (Exception notFoundException) {
                try {
                    keyStore.load(null, password.toCharArray());
                } catch (IOException e) {
                }
            }
        }
        if (!keyStore.containsAlias(messageKeyAlias)) {
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            SecureRandom secureRandom = new SecureRandom();
            int keyBitSize = 256;
            generator.init(keyBitSize, secureRandom);
            SecretKey key = generator.generateKey();
            keyStore.setKeyEntry(messageKeyAlias, key, password.toCharArray(), null);
            try (FileOutputStream keyStoreOutputStream = new FileOutputStream(keyStoreFilePath)) {
                keyStore.store(keyStoreOutputStream, password.toCharArray());
            } catch (IOException e) {
                // TODO: handle exception
            }
        }
    }

    public Key getKey() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        return keyStore.getKey(messageKeyAlias, "".toCharArray());
    }
}
