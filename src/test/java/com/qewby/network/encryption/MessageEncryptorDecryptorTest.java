package com.qewby.network.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessageEncryptorDecryptorTest {
    @Test
    public void defaultDecryptorConstructor() {
        new MessageDecryptor();
    }

    @Test
    public void defaultEncryptorConstructor() {
        new MessageEncryptor();
    }

    @Test
    public void testEncryptedThenDecryptedTextEqualsOrigin() throws Exception {
        String origin = "{text: some JSON like data}";
        byte[] encrypted = MessageEncryptor.encrypt(origin.getBytes());
        byte[] decrypted = MessageDecryptor.decrypt(encrypted);
        String result = new String(decrypted);
        assertEquals(origin, result);
    }
}
