package com.passManagerTest.serviceTest;

import com.passManager.service.PasswordGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordGeneratorTest {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{};:',./?`~";

    @Test
    public void testGeneratePassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();

        // Test for length 0
        assertEquals(8, passwordGenerator.generatePassword(8).length());

        // Test for length 1
        assertEquals(16, passwordGenerator.generatePassword(16).length());

    }
}
