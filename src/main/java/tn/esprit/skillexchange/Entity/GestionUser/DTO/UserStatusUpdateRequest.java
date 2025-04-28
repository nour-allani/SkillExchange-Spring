package tn.esprit.skillexchange.Entity.GestionUser.DTO;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.skillexchange.Entity.GestionUser.UserStatus;

@Getter
@Setter
public class UserStatusUpdateRequest {
    private UserStatus status;
}
