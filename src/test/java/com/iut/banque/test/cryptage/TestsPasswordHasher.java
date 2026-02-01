package com.iut.banque.test.cryptage;

import com.iut.banque.cryptage.PasswordHasher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestsPasswordHasher {

    private PasswordHasher passwordHasher;

    @Before
    public void setUp() {
         passwordHasher = new PasswordHasher();
    }

    @Test
    public void testPasswordHasher() {
        String password = "password";
        Assert.assertTrue(passwordHasher.verifyPassword(password,
                passwordHasher.hashPassword(password)));
    }

    @Test
    public void testHashPassword() {
        String password = "password";
        Assert.assertFalse(passwordHasher.verifyPassword("fdffdf",
                passwordHasher.hashPassword(password)));
    }

    @Test
    public void testHashPasswordNotNullOrEmpty() {
        String hash = passwordHasher.hashPassword("hello");
        Assert.assertNotNull(hash);
        Assert.assertFalse(hash.isEmpty());
    }

    @Test
    public void testVerifyCorrectPassword() {
        String password = "superSecret123";
        String hash = passwordHasher.hashPassword(password);
        Assert.assertTrue(passwordHasher.verifyPassword(password, hash));
    }

    @Test
    public void testVerifyIncorrectPassword() {
        String password = "superSecret123";
        String wrongPassword = "badPass";
        String hash = passwordHasher.hashPassword(password);

        Assert.assertFalse(passwordHasher.verifyPassword(wrongPassword, hash));
    }

    @Test
    public void testHashFormat() {
        String hash = passwordHasher.hashPassword("hello");

        String[] parts = hash.split(":");
        Assert.assertEquals("Le hash doit avoir 3 parties", 3, parts.length);

        // Vérifie que chaque partie est non vide
        Assert.assertFalse(parts[0].isEmpty());
        Assert.assertFalse(parts[1].isEmpty());
        Assert.assertFalse(parts[2].isEmpty());
    }

    @Test
    public void testUniqueHashForSamePassword() {
        String password = "testPassword";
        String hash1 = passwordHasher.hashPassword(password);
        String hash2 = passwordHasher.hashPassword(password);

        // Les hash doivent être différents à cause du salt aléatoire
        Assert.assertNotEquals(hash1, hash2);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidStoredHashFormatThrows() {
        passwordHasher.verifyPassword("test", "formatInvalide");
    }
}
