package alura.foro.foroAPI.topico;

import java.time.LocalDate;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDate Creacion,
		EstatusTopico estatus, String autor, Curso curso) {

}
