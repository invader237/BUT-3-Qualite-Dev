package com.iut.banque.controller;

import com.iut.banque.cryptage.PasswordHasher;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Utilisateur;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("changePasswordAction")
public class ChangePasswordAction extends ActionSupport {

    private Connect connect;
    private IDao dao;
    private PasswordHasher passwordHasher;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public ChangePasswordAction() {
    }

    // constructeur avec injection (optionnel)
    @Autowired
    public ChangePasswordAction(IDao dao, PasswordHasher passwordHasher) {
        this.dao = dao;
        this.passwordHasher = passwordHasher;
    }

    @Autowired
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setPasswordHasher(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    // 🔹 SETTERS (utilisés par Struts ou Spring)
    public void setConnect(Connect connect) { this.connect = connect; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    // 🔹 ACTION PRINCIPALE
    public String changePassword() {
        System.out.println("🔐 Tentative de changement de mot de passe...");

        Utilisateur user = null;

        // ✅ Si Struts n’a pas injecté "connect", crée-le proprement
        if (connect == null) {
            connect = new Connect();
        }

        user = connect.getConnectedUser();
        if (user == null) {
            System.out.println("⚠️ Aucun utilisateur connecté.");
            return LOGIN;
        }

        System.out.println("👤 Utilisateur trouvé : " + user.getNom() + " " + user.getPrenom());

        // ✅ Vérifie l'ancien mot de passe
        if (!passwordHasher.verifyPassword(oldPassword, user.getUserPwd())) {
            System.out.println("❌ Ancien mot de passe incorrect");
            addActionError("Ancien mot de passe incorrect");
            return INPUT;
        }

        // ✅ Vérifie la confirmation
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("❌ Les mots de passe ne correspondent pas");
            addActionError("Les mots de passe ne correspondent pas");
            return INPUT;
        }

        // ✅ Hash et mise à jour
        String hashed = passwordHasher.hashPassword(newPassword);
        user.setUserPwd(hashed);
        dao.updateUser(user);

        System.out.println("✅ Mot de passe changé avec succès !");

        return SUCCESS;
    }
}
