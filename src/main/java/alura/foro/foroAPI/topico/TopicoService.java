package alura.foro.foroAPI.topico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;

@Service
public class TopicoService {
	@Autowired
	TopicoRepository repository;

	public DatosRespuestaTopico crearTopico(DatosRegistroTopico registroTopico) {

		if (repository.existsBytitulo(registroTopico.titulo())) {
			throw new ValidationException("Este topico ya existe");

		}

		Topico topico = new Topico(registroTopico);
		repository.save(topico);

		return new DatosRespuestaTopico(topico);

	}

	public void verificarExistenciadelTopico(Long id) {

		if (!repository.existsById(id)) {
			throw new ValidationException("Este topico no existe");
		}

	}

}
