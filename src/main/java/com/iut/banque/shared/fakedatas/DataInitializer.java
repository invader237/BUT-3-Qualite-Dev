package com.iut.banque.shared.fakedatas;

import com.iut.banque.client.domain.catalog.ClientCatalog;
import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.compte.domain.catalog.CompteCatalog;
import com.iut.banque.compteavecdecouvert.domain.entity.CompteAvecDecouvert;
import com.iut.banque.comptesansdecouvert.domain.entity.CompteSansDecouvert;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.gestionnaire.domain.entity.Gestionnaire;
import com.iut.banque.utilisateur.domain.catalog.UtilisateurCatalog;
import com.iut.banque.utilisateur.infra.rest.UtilisateurController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("dev")
@Transactional(readOnly = false)
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurCatalog utilisateurCatalog;
    private final ClientCatalog clientCatalog;
    private final CompteCatalog compteCatalog;

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    public void initUtilisateurs() throws IllegalFormatException {
        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "Lidell",
                        "Alice",
                        "789, grande rue, Metz",
                        true,
                        "a.lidell1",
                        "100000:oYb4JmADeEw06MZSadv/Ng==:SF4iyWguHcJaJyfN3KWOVmulpHYscLjG46G/3/HLpnM=",
                        "9865432100"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "TEST NOM",
                        "TEST PRENOM",
                        "TEST ADRESSE",
                        true,
                        "c.exist1",
                        "100000:RO8y1GgT/LgLtJwYXDUEvQ==:Qw5O+8PRhvO4+6Wh4/0QqqhWl9utVxM19dpo2bPmuSk=",
                        "0101010101"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "Dupont",
                        "Daniel",
                        "a",
                        true,
                        "d.dupont1",
                        "100000:5UUvVxSGRcK2bcWA/yFt+Q==:ASRHSxN2+p1HgBUJYxAF0/AiFbW2Z8Z1NfROfErSSuA=",
                        "1234337890"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "TEST NOM",
                        "TEST PRENOM",
                        "TEST ADRESSE",
                        true,
                        "g.descomptes1",
                        "100000:uPgTjNhxIXPrMY8erP+/iA==:75PkpHzPYfdFrnE1w7aK1Rwo8AUG3Hd6AitGMU8TStk=",
                        "1000000001"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "TEST NOM",
                        "TEST PRENOM",
                        "TEST ADRESSE",
                        true,
                        "g.descomptesvides1",
                        "100000:lReQYSilLh2zvSq5bt2zhw==:twfyh03Bp4i/Bv597vMLSStoEZ0HUi1/lSaSp9FZ29M=",
                        "0000000002"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "TEST NOM",
                        "TEST PRENOM",
                        "TEST ADRESSE",
                        true,
                        "g.exist1",
                        "100000:hIfrXX1UerbId8VakfczVQ==:2br6TjO3pPUxPVhwomk8Ib2lF1joEbZinuaLiaecJFU=",
                        "1010101010"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "TEST NOM",
                        "TEST PRENOM",
                        "TEST ADRESSE",
                        true,
                        "g.pasdecompte1",
                        "100000:AY2XDv1XgkZJPUsoMGC/7Q==:sunZfeq6YF7nken/wKlnCitywmtxd9WL4rLeFpoGoZk=",
                        "5544554455"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "Doe",
                        "Jane",
                        "456, grand boulevard, Brest",
                        true,
                        "j.doe1",
                        "100000:WaVmqDXptjL64eDHedLsIQ==:1Xbw5QD+KzXhocpVUkm9AU61HYDazI5pm7WJkqwxn0g=",
                        "1234567890"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "Doe",
                        "John",
                        "457, grand boulevard, Perpignan",
                        true,
                        "j.doe2",
                        "100000:NU3O9LDqOY0ociwzly4psg==:iHoR5dHJqGSmM3iTm+RSvdkCoT7kqYUS+615G+HvBPA=",
                        "0000000001"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
                new Client(
                        "diego",
                        "maradona",
                        "123, grande rue, Marseille",
                        true,
                        "d.maradona1",
                        "100000:ZlNYw+cA0UGlKMvjqQQxnA==:8kHrziiVHTn/qAv9xhhpWLfOfI7ecT4L3GnV7BFGgtg=",
                        "1234557890"
                )
        );

        utilisateurCatalog.enregistrerUtilisateur(
            new Gestionnaire(
                    "Smith",
                "Joe",
                "123, grande rue, Metz",
                true,
                "admin",
                "100000:ZlNYw+cA0UGlKMvjqQQxnA==:8kHrziiVHTn/qAv9xhhpWLfOfI7ecT4L3GnV7BFGgtg="
                )
        );

    }

    public void initComptes() throws IllegalFormatException, IllegalOperationException {

        Client jDoe1 = clientCatalog.rechercherParUserId("j.doe1");
        Client jDoe2 = clientCatalog.rechercherParUserId("j.doe2");
        Client gDescomptes = clientCatalog.rechercherParUserId("g.descomptes1");
        Client gDescomptesVides = clientCatalog.rechercherParUserId("g.descomptesvides1");
        Client aLidell = clientCatalog.rechercherParUserId("a.lidell1");

        // ---------- COMPTES AVEC DÉCOUVERT ----------

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("AB7328887341", 4242, 123, jDoe2)
        );


        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("AV1011011011", 5, 100, gDescomptes)
        );

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("CA0000000000", 42, 42, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("CA1000000000", 0, 42, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("KL4589219196", 0, 150, gDescomptesVides)
        );

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("TD0398455576", 23, 500, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteAvecDecouvert("XD1829451029", -48, 100, jDoe1)
        );

        // ---------- COMPTES SANS DÉCOUVERT ----------

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("BD4242424242", 100, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("CS2000000000", 42, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("CS3000000000", 0, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("IO1010010001", 6868, jDoe2)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("KO7845154956", 0, gDescomptesVides)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("LA1021931215", 100, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("MD8694030938", 500, jDoe1)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("PP1285735733", 37, aLidell)
        );

        compteCatalog.enregistrerCompte(
                new CompteSansDecouvert("SA1011011011", 10, gDescomptes)
        );
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initialisation des données de test...");
        initUtilisateurs();
        log.info("Initialisation des utilisateur terminée.");
        initComptes();
        log.info("Initialisation des comptes terminée.");
    }
}
