package com.passManagerTest.serviceTest;

import com.passManager.service.PasswordGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    private PasswordGenerator passwordGenerator;

    @Before
    public void setUp() {
        passwordGenerator = new PasswordGenerator();
    }

    @Test
    public void testGeneratePassword() {

        // Test for length 8 - note the length is after encryption (encryption/decryption is tested below)
        assertEquals(24, passwordGenerator.generatePassword(8).length());

        // Test for length 16 - note the length is after encryption (encryption/decryption is tested below)
        assertEquals(44, passwordGenerator.generatePassword(16).length());
    }

    @Test
    public void testUserDefinedPasswordValid() {
        // Test user-defined password encryption with a valid password
        String originalPassword = "ValidPass123";
        String encryptedPassword = passwordGenerator.userDefinedPassword(originalPassword);

        assertNotNull(encryptedPassword);
        assertNotEquals(originalPassword, encryptedPassword);
    }

    @Test
    public void testUserDefinedPasswordNull() {
        // Test user-defined password encryption with a null password
        String originalPassword = null;
        String encryptedPassword = passwordGenerator.userDefinedPassword(originalPassword);

        assertNull(encryptedPassword);
    }

    @Test
    public void testUserDefinedPasswordShort() {
        // Test user-defined password encryption with a short password
        String originalPassword = "Short";
        String encryptedPassword = passwordGenerator.userDefinedPassword(originalPassword);

        assertNull(encryptedPassword);
    }

    @Test
    public void testEncryptAndDecrypt() {
        String originalText = "This is a secret message";
        String secretKey = "SecretKey123";

        // Encrypt
        String encryptedText = PasswordGenerator.encrypt(originalText, secretKey);

        assertNotNull(encryptedText);

        // Decrypt
        String decryptedText = PasswordGenerator.decrypt(encryptedText, secretKey);

        assertNotNull(decryptedText);
        assertEquals(originalText, decryptedText);

        // Test with invalid secret key
        String invalidKey = "InvalidKey456";
        String decryptedWithInvalidKey = PasswordGenerator.decrypt(encryptedText, invalidKey);

        assertNull(decryptedWithInvalidKey);
    }
}
