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
}
