package com.iut.banque.cryptage;

import com.iut.banque.dao.DaoHibernate;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class HashOldPassword {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 256;
    private static final int ITERATIONS = 100_000;

    private final IDao dao;
    private final PasswordHasher passwordHasher;

    @Autowired
    public HashOldPassword(IDao dao, PasswordHasher passwordHasher) {
        this.dao = dao;
        this.passwordHasher = passwordHasher;
    }

    public void run() {
        System.out.println("Hashing old passwords ...");

        Map<String, Client> clients = dao.getAllClients();
        Map<String, Gestionnaire> gestionnaires = dao.getAllGestionnaires();

        clients.forEach((key, client) -> {
            if (!isValidHashFormat(client.getUserPwd())) {
                System.out.println("Invalid hash format for" + client.getUserId());
                client.setUserPwd(passwordHasher.hashPassword(client.getUserPwd()));
                dao.updateUser(client);
            }
        });

        gestionnaires.forEach((key, gestionnaire) -> {
            if (!isValidHashFormat(gestionnaire.getUserPwd())) {
                System.out.println("Invalid hash format for" + gestionnaire.getUserId());
                gestionnaire.setUserPwd(passwordHasher.hashPassword(gestionnaire.getUserPwd()));
                dao.updateUser(gestionnaire);
            }
        });

        System.out.println("DONE");
    }

    public static boolean isValidHashFormat(String hash) {
        if (hash == null || hash.isEmpty()) {
            return false;
        }

        String[] parts = hash.split(":");
        if (parts.length != 3) {
            return false;
        }

        try {
            int iterations = Integer.parseInt(parts[0]);
            if (iterations <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        try {
            Base64.getDecoder().decode(parts[1]);
            Base64.getDecoder().decode(parts[2]);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}