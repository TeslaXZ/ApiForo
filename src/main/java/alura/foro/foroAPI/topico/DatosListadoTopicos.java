package alura.foro.foroAPI.topico;

import java.time.LocalDate;

public record DatosListadoTopicos(Long id, String titulo, String mensaje, LocalDate fechaCreacion,
		EstatusTopico estatusTopico, String autor, Curso curso) {

	public DatosListadoTopicos(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getCreacion(),
				topico.getEstatus(), topico.getAutor(), topico.getCurso());
	}

}
