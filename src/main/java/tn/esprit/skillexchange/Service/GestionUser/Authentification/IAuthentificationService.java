package tn.esprit.skillexchange.Service.GestionUser.Authentification;

import tn.esprit.skillexchange.Entity.GestionUser.Authentication.RefreshTokenRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignInRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.SignUpRequest;
import tn.esprit.skillexchange.Entity.GestionUser.Authentication.jwtAuthentifactionResponse;
import tn.esprit.skillexchange.Entity.GestionUser.User;

public interface IAuthentificationService {
    public User signup(SignUpRequest signUpRequest);

    public jwtAuthentifactionResponse signin(SignInRequest signInRequest);
    jwtAuthentifactionResponse  refreshToken(RefreshTokenRequest refreshTokenRequest) ;
}
