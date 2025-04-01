package tn.esprit.skillexchange.Entity.Mailing;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCodeRequest {
    private String email;
    private String code;
}
