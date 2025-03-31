package tn.esprit.skillexchange.Controller.GestionUser;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.skillexchange.Entity.GestionUser.Badge;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.ChangePasswordRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Ban.BanRequest;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;

import java.io.IOException;
import java.util.*;

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


    @PostMapping("/{id}/ban")
    public ResponseEntity<?> banUser(
            @PathVariable Long id,
            @RequestBody BanRequest banRequest) {

        userService.banUser(id, banRequest.getReason(), banRequest.getEndDate(), banRequest.getBannedBy());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unban")
    public ResponseEntity<?> unbanUser(@PathVariable Long id) {
        userService.unbanUser(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{userId}/badges/{badgeId}")
    public ResponseEntity<User> assignBadgeToUser(
            @PathVariable Long userId,
            @PathVariable Long badgeId
    ) {
        userService.assignBadgeToUser(userId, badgeId);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{userId}/badges/{badgeId}")
    public ResponseEntity<User> removeBadgeFromUser(
            @PathVariable Long userId,
            @PathVariable Long badgeId
    ) {
        userService.removeBadgeFromUser(userId, badgeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/badges")
    public ResponseEntity<Set<Badge>> getUserBadges(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserBadges(userId));
    }

}
