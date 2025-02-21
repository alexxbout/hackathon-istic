package fr.istic.app.authApi.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String nom;
    private String prenom;
    private String mail;
    private String image;
    private boolean firstLogin;
    private String role;

}
