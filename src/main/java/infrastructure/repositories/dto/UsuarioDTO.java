package infrastructure.repositories.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String login;
    private String senha;
    private String email;
}