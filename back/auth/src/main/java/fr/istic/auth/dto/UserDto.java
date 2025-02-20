package fr.istic.auth.dto;
public class UserDto {
    private String nom;       // Last name
    private String prenom;    // First name
    private String email;     // Email address
    private String password;  // Password (for creation/update; hash in production)
    private String role;      // Role: "RH", "CDP" or "ADMIN"

    private String image;

    // Getters and setters

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }
}

