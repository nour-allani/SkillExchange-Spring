package tn.esprit.skillexchange.Service.GestionUser.Authentification;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.RefreshTokenRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignInRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignUpRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.jwtAuthentifactionResponse;
import tn.esprit.skillexchange.Entity.GestionUser.Role;
import tn.esprit.skillexchange.Entity.GestionUser.User;
import tn.esprit.skillexchange.Repository.GestionUser.UserRepo;
import tn.esprit.skillexchange.Service.GestionUser.IUserService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthentificationServiceImpl implements IAuthentificationService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;  // ✅ Injection correcte
    private final AuthenticationManager authenticationManager;

    private final IJWTService jwtService;

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setPassword(signUpRequest.getPassword());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }


    public jwtAuthentifactionResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())) ;
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshTocken = jwtService.generateRefreshToken(new HashMap<>(),user);

        jwtAuthentifactionResponse jwtAuthentifactionResponse = new jwtAuthentifactionResponse();
        jwtAuthentifactionResponse.setToken(jwt);
        jwtAuthentifactionResponse.setRefreshToken(refreshTocken);
        return jwtAuthentifactionResponse;
    }


    public jwtAuthentifactionResponse  refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {

            var jwt = jwtService.generateToken(user);
            jwtAuthentifactionResponse jwtAuthentifactionResponse = new jwtAuthentifactionResponse();
            jwtAuthentifactionResponse.setToken(jwt);
            jwtAuthentifactionResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthentifactionResponse;



        }
        return null;


    }
}