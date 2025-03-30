package tn.esprit.skillexchange.Entity.GestionUser.DTO.Authentication;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import tn.esprit.skillexchange.Entity.GestionUser.Role;

@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
