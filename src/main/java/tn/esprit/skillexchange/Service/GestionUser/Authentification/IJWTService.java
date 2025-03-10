package tn.esprit.skillexchange.Service.GestionUser.Authentification;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface IJWTService {
    String extractUserName(String token);
    public String generateToken(UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
