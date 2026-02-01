package com.iut.banque.shared.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Arrays;

@Configuration
public class PasswordService {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Custom PBKDF2WithHmacSHA256 encoder to match legacy format iterations:salt:hash
        return new PasswordEncoder() {
            private final SecureRandom random = new SecureRandom();
            private final int SALT_LENGTH = 16; // bytes
            private final int ITERATIONS = 100_000;
            private final int HASH_LENGTH = 256; // bits
            private final String ALGO = "PBKDF2WithHmacSHA256";

            @Override
            public String encode(CharSequence rawPassword) {
                try {
                    byte[] salt = new byte[SALT_LENGTH];
                    random.nextBytes(salt);
                    PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), salt, ITERATIONS, HASH_LENGTH);
                    SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
                    byte[] hash = skf.generateSecret(spec).getEncoded();
                    return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    throw new RuntimeException("Erreur lors du hash du mot de passe", e);
                }
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                try {
                    String[] parts = encodedPassword.split(":");
                    if (parts.length != 3) return false;
                    int iterations = Integer.parseInt(parts[0]);
                    byte[] salt = Base64.getDecoder().decode(parts[1]);
                    byte[] storedHash = Base64.getDecoder().decode(parts[2]);

                    PBEKeySpec spec = new PBEKeySpec(rawPassword.toString().toCharArray(), salt, iterations, storedHash.length * 8);
                    SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
                    byte[] hash = skf.generateSecret(spec).getEncoded();

                    return Arrays.equals(hash, storedHash);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException | NumberFormatException e) {
                    throw new RuntimeException("Erreur lors de la vérification du mot de passe", e);
                }
            }
        };
    }
}
