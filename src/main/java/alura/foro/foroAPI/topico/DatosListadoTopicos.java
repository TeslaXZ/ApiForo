package alura.foro.foroAPI.topico;

import java.time.LocalDate;
import java.util.List;


public record DatosListadoTopicos(Long id, String titulo, String mensaje, LocalDate fechaCreacion,
		EstatusTopico estatusTopico, String autor, Curso curso, List<DatosListadoRespuestas> respuestas) {

	public DatosListadoTopicos(Topico topico, List<DatosListadoRespuestas> listadoRespuesta) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getCreacion(),
				topico.getEstatus(), topico.getAutor(), topico.getCurso(), listadoRespuesta);
	}

	
	

	
}
