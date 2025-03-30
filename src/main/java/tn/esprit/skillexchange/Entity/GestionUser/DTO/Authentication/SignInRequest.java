package tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
