package tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;

    private String newPassword;

}
