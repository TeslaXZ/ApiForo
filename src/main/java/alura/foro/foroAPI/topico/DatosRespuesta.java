package alura.foro.foroAPI.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRespuesta( 
		@NotBlank
		String usuario,
		@NotBlank
		String respuesta,
		@NotNull
		Long topicoid) {

}
