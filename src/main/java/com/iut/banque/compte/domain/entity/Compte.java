package com.iut.banque.compte.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.iut.banque.client.domain.entity.Client;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.InsufficientFundsException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * Classe repr�sentant un compte quelconque.
 *
 * La strat�gie d'héritage choisie est SINGLE_TABLE. Cela signifie que tous les
 * objets de cette classe et des classes filles sont enregistrés dans une seule
 * table dans la base de donnée. Les champs non utilisés par la classe sont
 * NULL.
 *
 * Lors d'un chargement d'un objet appartenant à une des classes filles, le type
 * de l'objet est choisi grâce à la colonne "avecDecouvert" (c'est une colonne
 * de discrimination).
 */
@Entity
@Table(name = "Compte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "avecDecouvert", discriminatorType = DiscriminatorType.STRING, length = 5)
@Getter @Setter
public abstract class Compte {

    /**
     * L'identifiant unique du compte.
     *
     * Utilisé comme clé primaire dans la base de données.
     */
    @Id
    @Column(name = "numeroCompte")
    protected String numeroCompte;

    /**
     * Le solde du compte.
     */
    @Column(name = "solde")
    protected double solde;

    /**
     * Le propriétaire du compte.
     *
     * L'association "many-to-one" signifie que le compte n'a qu'un seul
     * propriétaire mais que chaque client peut avoir plusieurs compte.
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    //TODO: utiliser des dtos pour retirer l'annotation jackson des entités
    @JsonBackReference
    protected Client owner;

    protected Compte(String numeroCompte, double solde, Client client) throws IllegalFormatException {
        this.setNumeroCompte(numeroCompte);
        this.solde = solde;
        this.owner = client;
    }

    public Compte() {

    }

    /**
     * Setter du numéro de compte.
     *
     * @param numeroCompte
     *            : le numéro de compte à changer
     * @throws IllegalFormatException
     *             : dans le cas où le format du numéro de compte n'est pas
     *             correct.
     */
    private void setNumeroCompte(String numeroCompte) throws IllegalFormatException {
        if (numeroCompte == null) {
            throw new IllegalFormatException("Le numéro de compte ne peut pas être nul");
        } else if (!Compte.checkFormatNumeroCompte(numeroCompte)) {
            throw new IllegalFormatException("Le numéro de compte ne respecte pas le format");
        }
        this.numeroCompte = numeroCompte;
    }

    /**
     * Débite du compte le montant passé en paramètre.
     *
     * @param montant
     *            : le montant à débiter
     * @throws InsufficientFundsException
     *             : dans le cas où le montant est trop élevé
     */
    public abstract void debiter(double montant) throws InsufficientFundsException, IllegalFormatException;

    /**
     * Crédite sur le compte le montant passé en paramètre.
     *
     * @param montant
     *            : le montant à créditer
     * @throws IllegalFormatException
     *             : si le montant est négatif
     */
    public void crediter(double montant) throws IllegalFormatException {
        if (montant < 0) {
            throw new IllegalFormatException("Le montant ne peux être négatif");
        }
        solde += montant;
    }

    /**
     * Getter pour le nom de la classe.
     *
     * Utilisé pour déterminer le type de compte dans les pages JSP.
     *
     * @return String, le nom de la classe de l'objet
     */
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Compte [numeroCompte=" + numeroCompte + ", solde=" + solde + ", owner=" + owner.getUserId() + "]";
    }

    /**
     * Fonction qui va vérifier le string d'entrée si il correspond au format
     * attendu pour un numéro de compte, à savoir XX0000000000 avec X des
     * lettres de A à Z et 0 des chiffres (Par exemple FR0123456789)
     *
     * @param s
     *            : String d'entrée qu'on veut comparer au format attendu
     * @return boolean : résultat de la comparaison. True si le format est
     *         correct, false sinon
     */
    public static boolean checkFormatNumeroCompte(String s) {
        return Pattern.matches("[A-Z]{2}[0-9]{10}", s);
    }
}