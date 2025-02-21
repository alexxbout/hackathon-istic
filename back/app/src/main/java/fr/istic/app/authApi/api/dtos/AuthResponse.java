package fr.istic.app.authApi.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String role;
    private String nom;
    private String prenom;
    private String mail;
    private String image;

    private boolean isFirstLogin;
}