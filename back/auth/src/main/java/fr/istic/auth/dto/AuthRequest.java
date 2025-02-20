package fr.istic.auth.dto;

public class AuthRequest {
    private String mail;
    private String password;

    // Default constructor
    public AuthRequest() {
    }

    // Constructor with parameters
    public AuthRequest(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    // Getters and Setters
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

