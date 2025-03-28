package tn.esprit.skillexchange.Controller.GestionUser;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.ChangePasswordRequest;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.retrieveUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.retrieveUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.remove(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.retrieveUserByEmail(email);
    }

    @PatchMapping("/{id}")
    public User updateUserPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.updateUserPartially(id, updates);
    }

    @PostMapping("/{id}/image")
    public User uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        return userService.updateUserImage(id, base64Image);
    }


    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        userService.changePassword(userDetails.getUsername(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );
        return ResponseEntity.ok().build();
    }
}
