    package fr.istic.app.authApi.api.controller;
    
    import fr.istic.app.authApi.api.service.LdapUserService;
    import fr.istic.app.authApi.api.dtos.AuthResponse;
    import fr.istic.app.authApi.api.dtos.AuthRequest;
    import fr.istic.app.authApi.api.dtos.UserDto;
    import fr.istic.app.authApi.api.dtos.UserInfoDto;
    import fr.istic.app.authApi.api.utils.SessionUtils;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    import java.util.Map;
    
    @RestController
    @RequestMapping("/users")
    public class LdapUserController {
    
        private final LdapUserService ldapUserService;


        public LdapUserController(LdapUserService ldapUserService) {
    
            this.ldapUserService = ldapUserService;
        }
    
        // Create User
        @PostMapping("/create")
        public ResponseEntity<?> createUser(@RequestBody UserDto userDto, HttpServletRequest request) {
            if (SessionUtils.isAdmin(request)) {
                return ldapUserService.createUser(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to create a user.");
            }
        }
    
        // Login User
        @PostMapping("/login")
        public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
    
            return ldapUserService.loginUser(authRequest, request);
        }
    
        @PostMapping("/logout")
        public ResponseEntity<String> logout(HttpServletRequest request) {
            return ldapUserService.logout(request);
        }

        @PutMapping("/reinitializePassword/{mail}")
        public ResponseEntity<String> reinitializePasswordUser(@PathVariable String mail, HttpServletRequest request) {
            if (SessionUtils.isAdmin(request)) {
                return ldapUserService.reinitializePasswordUser(mail,request);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to reset passwords.");
            }
        }


        // Get All Users (Authenticated Request)
        @GetMapping("/all")
        public ResponseEntity<List<UserInfoDto>> getAllUsers(HttpServletRequest request) {
            if (SessionUtils.isAdmin(request)) {
                return ldapUserService.getAllUsers(request);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }

        @GetMapping("/getSessionInfo")
        public ResponseEntity<UserInfoDto>  getSession(HttpServletRequest request) {
            UserInfoDto userInfoDto = SessionUtils.getInformationUser(request);
            if (userInfoDto == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } else {
                return ResponseEntity.ok(userInfoDto);

            }
        }
    
        // Update Password
        @PutMapping("/updatePassword")
        public ResponseEntity<String> updatePassword(HttpServletRequest request, @RequestBody Map<String, String> password) {
            return ldapUserService.updatePassword(request, password);
        }
    
        // Update Profile Picture
        @PutMapping("/updatePicture")
        public ResponseEntity<String> updatePicture(HttpServletRequest request, @RequestBody Map<String,String> imagePath) {
            return ldapUserService.updatePicture(request, imagePath);
        }
    
        // Delete User
        @DeleteMapping("/delete/{email}")
        public ResponseEntity<String> deleteUser(@PathVariable String email, HttpServletRequest request) {
            if (SessionUtils.isAdmin(request)) {
                return ldapUserService.deleteUser(email,request);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete users.");
            }
        }
    }
