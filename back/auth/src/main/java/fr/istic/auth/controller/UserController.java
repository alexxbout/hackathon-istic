package fr.istic.auth.controller;

import fr.istic.auth.dto.AuthRequest;
import fr.istic.auth.dto.AuthResponse;
import fr.istic.auth.dto.UserDto;
import fr.istic.auth.dto.UserInfoDto;
import fr.istic.auth.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsersDetails());
    }

    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<String> updatePasswordUser(@PathVariable String userId, @RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(userService.updatePasswordUser(userId, userDto));
    }


    @PutMapping("/reinitializePassword/{userId}")
    public ResponseEntity<String> reinitializePasswordUser(@PathVariable String userId) throws Exception {
        return ResponseEntity.ok(userService.reinitializePasswordUser(userId));
    }

    @PutMapping("/updatePicture/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) throws Exception {
        return ResponseEntity.ok(userService.updateProfilePictureUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        return ResponseEntity.ok(userService.login(authRequest));
    }
}
