package com.qewby.network.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptorDecryptorTest {
    @Test
    public void defaultDecryptorConstructor() {
        new Decryptor();
    }

    @Test
    public void defaultEncryptorConstructor() {
        new Encryptor();
    }

    @Test
    public void testEncryptedThenDecryptedTextEqualsOrigin() throws Exception {
        String origin = "{text: some JSON like data}";
        byte[] encrypted = Encryptor.encrypt(origin.getBytes());
        byte[] decrypted = Decryptor.decrypt(encrypted);
        String result = new String(decrypted);
        assertEquals(origin, result);
    }
}
