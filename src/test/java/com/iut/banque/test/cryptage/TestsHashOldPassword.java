package com.iut.banque.test.cryptage;

import com.iut.banque.cryptage.HashOldPassword;
import org.junit.Assert;
import org.junit.Test;

public class TestsHashOldPassword {

    private static final String VALID_HASH = "1000:c2FsdA==:aGFzaA==";

    @Test
    public void isValidHashFormat_nullOrEmpty_returnsFalse() {
        Assert.assertFalse(HashOldPassword.isValidHashFormat(null));
        Assert.assertFalse(HashOldPassword.isValidHashFormat(""));
    }

    @Test
    public void isValidHashFormat_badPartsOrIterations_returnsFalse() {
        Assert.assertFalse(HashOldPassword.isValidHashFormat("1000:c2FsdA=="));
        Assert.assertFalse(HashOldPassword.isValidHashFormat("1000:c2FsdA==:aGFzaA==:x"));
        Assert.assertFalse(HashOldPassword.isValidHashFormat("abc:c2FsdA==:aGFzaA=="));
        Assert.assertFalse(HashOldPassword.isValidHashFormat("0:c2FsdA==:aGFzaA=="));
    }

    @Test
    public void isValidHashFormat_badBase64_returnsFalse() {
        Assert.assertFalse(HashOldPassword.isValidHashFormat("1000:not-base64:aGFzaA=="));
        Assert.assertFalse(HashOldPassword.isValidHashFormat("1000:c2FsdA==:%%%"));
    }

    @Test
    public void isValidHashFormat_valid_returnsTrue() {
        Assert.assertTrue(HashOldPassword.isValidHashFormat(VALID_HASH));
    }
}
