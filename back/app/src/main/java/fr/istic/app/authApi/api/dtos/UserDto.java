package fr.istic.app.authApi.api.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String nom;       // Last name
    private String prenom;    // First name
    private String email;     // Email address
    private String password;  // Password (for creation/update; hash in production)
    private String role;      // Role: "RH", "CDP" or "ADMIN"
    private String image;

}
