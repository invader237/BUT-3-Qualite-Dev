package com.iut.banque.test.cryptage;

import com.iut.banque.cryptage.PasswordHasher;
import org.junit.Assert;
import org.junit.Test;

public class TestsPasswordHasher {

    @Test
    public void testPasswordHasher() {
        String password = "password";
        Assert.assertTrue(PasswordHasher.verifyPassword(password,
                PasswordHasher.hashPassword(password)));
    }

    @Test
    public void testHashPassword() {
        String password = "password";
        Assert.assertFalse(PasswordHasher.verifyPassword("fdffdf",
                PasswordHasher.hashPassword(password)));
    }
}
