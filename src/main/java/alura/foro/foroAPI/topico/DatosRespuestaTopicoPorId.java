package alura.foro.foroAPI.topico;

import java.time.LocalDate;
import java.util.List;

public record DatosRespuestaTopicoPorId(Long id, String titulo, String mensaje, LocalDate Creacion, EstatusTopico estatus,
		String autor, Curso curso, List<DatosListadoRespuestas> respuestas) {
	
	public DatosRespuestaTopicoPorId(Topico topico, List<DatosListadoRespuestas> respuestas) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getCreacion(),
				topico.getEstatus(), topico.getAutor(), topico.getCurso(),respuestas);
	}

}
