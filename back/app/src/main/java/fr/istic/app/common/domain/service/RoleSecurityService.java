package fr.istic.app.common.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service("roleSecurityService")
public class RoleSecurityService {
    public static boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("USER_ROLE");
        return "admin".equalsIgnoreCase(role);
    }
    public static boolean isRH(HttpSession session) {
        String role = (String) session.getAttribute("USER_ROLE");
        return "rh".equalsIgnoreCase(role);
    }
    public static boolean isCDP(HttpSession session) {
        String role = (String) session.getAttribute("USER_ROLE");
        return "cdp".equalsIgnoreCase(role);
    }

}
