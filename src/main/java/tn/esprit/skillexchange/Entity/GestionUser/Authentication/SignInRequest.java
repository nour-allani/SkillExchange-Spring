package tn.esprit.skillexchange.Entity.GestionUser.Authentication;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
