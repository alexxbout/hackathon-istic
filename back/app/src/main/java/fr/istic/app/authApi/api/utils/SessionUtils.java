package fr.istic.app.authApi.api.utils;

import fr.istic.app.authApi.api.dtos.AuthResponse;
import fr.istic.app.authApi.api.dtos.UserInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

public class SessionUtils {

    public static String getCurrentUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session == null) {
            return null; // No session found
        }
        return (String) request.getSession().getAttribute("USER_ROLE");
    }
    public static boolean isAdmin(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("USER_ROLE");
        return "admin".equalsIgnoreCase(role);
    }

    public static UserInfoDto getInformationUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session == null) {
            return null; // No session found
        }

        // Retrieve session attributes
        String mail = (String) session.getAttribute("USER_EMAIL");
        String nom = (String) session.getAttribute("USER_NOM");
        String prenom = (String) session.getAttribute("USER_PRENOM");
        String image = (String) session.getAttribute("USER_IMAGE");
        String role = (String) session.getAttribute("USER_ROLE");
        Boolean firstLogin = (Boolean) session.getAttribute("USER_FIRST_LOGIN");

        // If any value is null, return null
        if (mail == null || nom == null || prenom == null || role == null || firstLogin == null || image == null) {
            return null;
        }

        // Create and return UserInfoDto
        return new UserInfoDto(nom,prenom,mail,image,firstLogin,role);
    }



    public static boolean isCdp(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("USER_ROLE");
        return  "cdp".equalsIgnoreCase(role);
    }


    public boolean isRH(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("USER_ROLE");
        return  "rh".equalsIgnoreCase(role);
    }
}
