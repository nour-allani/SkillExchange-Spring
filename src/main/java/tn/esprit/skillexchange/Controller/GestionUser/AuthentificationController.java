package tn.esprit.skillexchange.Controller.GestionUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.RefreshTokenRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.SignInRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.SignUpRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.jwtAuthentifactionResponse;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionUser.Authentification.IAuthentificationService;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthentificationController {
    private final IAuthentificationService authentificationService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        try {
            return ResponseEntity.ok(authentificationService.signup(signUpRequest));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("message", e.getReason()));
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signinRequest){
        try {
            return ResponseEntity.ok(authentificationService.signin(signinRequest));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(Collections.singletonMap("message", e.getReason()));
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<jwtAuthentifactionResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authentificationService.refreshToken(refreshTokenRequest));
    }
}
