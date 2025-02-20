package fr.istic.auth.dto;

public class UserInfoDto {
    private String nom;
    private String prenom;
    private String mail;
    private String image;
    private boolean firstLogin;
    private String role;

    public UserInfoDto(String nom, String prenom, String mail, String image, boolean firstLogin,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.image = image;
        this.firstLogin = firstLogin;
        this.role = role;
    }

    public UserInfoDto(String nom, String prenom, String mail, String image, boolean firstLogin) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.image = image;
        this.firstLogin = firstLogin;
    }

    // Getters and setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isFirstLogin() { return firstLogin; }
    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
