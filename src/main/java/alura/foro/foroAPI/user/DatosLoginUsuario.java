package alura.foro.foroAPI.user;

import jakarta.validation.constraints.NotBlank;

public record DatosLoginUsuario(
		@NotBlank
		String login,
		@NotBlank
		String clave) {

}
