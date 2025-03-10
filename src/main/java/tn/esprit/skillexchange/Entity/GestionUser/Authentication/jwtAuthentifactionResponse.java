package tn.esprit.skillexchange.Entity.GestionUser.Authentication;

import lombok.Data;

@Data
public class jwtAuthentifactionResponse {
    private String token;
    private String refreshToken;
}
