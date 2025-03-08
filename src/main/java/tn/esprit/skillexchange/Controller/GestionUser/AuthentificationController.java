package tn.esprit.skillexchange.Controller.GestionUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.RefreshTokenRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignInRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignUpRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.jwtAuthentifactionResponse;
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
