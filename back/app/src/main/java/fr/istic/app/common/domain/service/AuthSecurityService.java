package fr.istic.app.common.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service("authSecurityService")
public class AuthSecurityService {
    public boolean isLoggedIn(HttpSession session){
        String mail = (String) session.getAttribute("USER_EMAIL");
        String nom = (String) session.getAttribute("USER_NOM");
        String prenom = (String) session.getAttribute("USER_PRENOM");
        String image = (String) session.getAttribute("USER_IMAGE");
        String role = (String) session.getAttribute("USER_ROLE");
        Boolean firstLogin = (Boolean) session.getAttribute("USER_FIRST_LOGIN");

        // If any value is null, return null
        if (mail == null || nom == null || prenom == null || role == null || firstLogin == null || image == null) {
            return false;
        }

        return true;

    }


}
