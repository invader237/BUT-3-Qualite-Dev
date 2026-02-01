package com.iut.banque.utilisateur.domain.entity;

import com.iut.banque.exceptions.IllegalFormatException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Classe représentant un utilisateur quelconque.
 *
 * La stratégie d'héritage choisie est SINGLE_TABLE. Cela signifie que tous les
 * objets de cette classe et des classes filles sont enregistrés dans une seule
 * table dans la base de donnée. Les champs non utilisés par la classe sont
 * NULL.
 *
 * Lors d'un chargement d'un objet appartenant à une des classes filles, le type
 * de l'objet est choisi grâce à la colonne "type" (c'est une colonne de
 * discrimination).
 */
@Entity
@Table(name = "Utilisateur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 15)
@Getter
@Setter
@RequiredArgsConstructor
public abstract class Utilisateur {

    /**
     * L'identifiant (unique) de l'utilisateur.
     *
     * Correspond à la clé primaire de l'utilisateur dans la BDD.
     */
    @Id
    @Column(name = "userId")
    private String userId;

    /**
     * Le mot de passe de l'utilisateur.
     *
     */
    @Column(name = "userPwd")
    private String userPwd;

    /**
     * Le nom de l'utilisateur.
     */
    @Column(name = "nom")
    private String nom;

    /**
     * Le prénom de l'utilisateur.
     */
    @Column(name = "prenom")
    private String prenom;

    /**
     * L'adresse physique de l'utilisateur.
     */
    @Column(name = "adresse")
    private String adresse;

    /**
     * Le sexe de l'utilisateur. Vrai si c'est un homme faux sinon.
     */
    @Column(name = "male")
    private boolean male;

    public Utilisateur(String nom, String prenom, String adresse, boolean homme, String usrId, String usrPwd) {
    }

    /**
     * @param userId
     *            : l'identifiant de l'utilisateur
     * @throws IllegalFormatException
     */
    public void setUserId(String userId) throws IllegalFormatException {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "Utilisateur [userId=" + userId + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse
                + ", male=" + male + ", userPwd=" + userPwd + "]";
    }

}
