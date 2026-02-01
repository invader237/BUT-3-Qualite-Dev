package com.iut.banque.gestionnaire.domain.entity;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.utilisateur.domain.entity.Utilisateur;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Cette classe représente un gestionnaire de comptes qui doit pouvoir accéder à
 * tous les comptes de sa banque.
 */
@Entity
@DiscriminatorValue("MANAGER")
@Getter
@Setter
@RequiredArgsConstructor
public class Gestionnaire extends Utilisateur {

    /**
     * Constructeur de Gestionnaire avec tous les champs de la classe comme
     * paramètres.
     *
     * Il est préférable d'utiliser une classe implémentant IDao pour créer un
     * objet au lieu d'appeler ce constructeur.
     * @throws IllegalFormatException
     */
    public Gestionnaire(String nom, String prenom, String adresse, boolean homme, String usrId, String usrPwd) throws IllegalFormatException {
        super(nom, prenom, adresse, homme, usrId, usrPwd);
        if ("".equals(usrId)) {
            throw new IllegalArgumentException("L'identifiant ne peux être vide.");
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Gestionnaire [nom=" + getNom() + ", prenom=" + getPrenom() + ", adresse=" + getAdresse() + ", male="
                + isMale() + ", userId=" + getUserId() + ", userPwd=" + getUserPwd() + "]";
    }
}