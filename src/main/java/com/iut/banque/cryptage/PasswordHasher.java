package com.iut.banque.cryptage;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class PasswordHasher {

    private static final int SALT_LENGTH = 16;       // 16 bytes = 128 bits
    private static final int HASH_LENGTH = 256;      // 256 bits
    private static final int ITERATIONS = 100_000;   // nombre d’itérations

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    private static final SecureRandom random = new SecureRandom();

    /**
     * Hash un mot de passe et retourne une chaîne contenant les itérations, le sel et le hash.
     * Format : iterations:salt:hash
     */
    public String hashPassword(String password) {
        try {
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
            byte[] hash = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();

            return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erreur lors du hash du mot de passe", e);
        }
    }

    /**
     * Vérifie si un mot de passe correspond au hash stocké.
     */
    public boolean verifyPassword(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] storedHash = Base64.getDecoder().decode(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, storedHash.length * 8);
            byte[] hash = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();

            // Comparaison sécurisée
            if (hash.length != storedHash.length) return false;
            int diff = 0;
            for (int i = 0; i < hash.length; i++) {
                diff |= hash[i] ^ storedHash[i];
            }
            return diff == 0;

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erreur lors de la vérification du mot de passe", e);
        }
    }

}
