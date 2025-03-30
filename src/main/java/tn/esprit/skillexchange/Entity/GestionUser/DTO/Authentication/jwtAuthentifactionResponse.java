package tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication;

import lombok.Data;

@Data
public class jwtAuthentifactionResponse {
    private String token;
    private String refreshToken;
}
