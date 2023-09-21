package alura.foro.foroAPI.topico;

import java.time.LocalDate;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDate Creacion, EstatusTopico estatus,
		String autor, Curso cursos) {

	public DatosRespuestaTopico(Topico topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getCreacion(), topico.getEstatus(),
				topico.getAutor(), topico.getCurso());
	}

	

}
