package alura.foro.foroAPI.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
		@NotBlank
		String nombre,
		@NotBlank
		@Email
		String correo,
		@NotBlank
		String login,
		@NotBlank
		String clave) {

}
