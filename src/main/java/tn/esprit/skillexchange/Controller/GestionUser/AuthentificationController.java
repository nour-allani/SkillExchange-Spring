package tn.esprit.skillexchange.Controller.GestionUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.RefreshTokenRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.SignInRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.SignUpRequest;
import tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication.jwtAuthentifactionResponse;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Service.GestionUser.Authentification.IAuthentificationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthentificationController {
    private final IAuthentificationService authentificationService;
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authentificationService.signup(signUpRequest));
    }


    @PostMapping("/signin")
    public ResponseEntity<jwtAuthentifactionResponse> signin(@RequestBody SignInRequest signinRequest){
        return ResponseEntity.ok(authentificationService.signin(signinRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<jwtAuthentifactionResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authentificationService.refreshToken(refreshTokenRequest));
    }
}
