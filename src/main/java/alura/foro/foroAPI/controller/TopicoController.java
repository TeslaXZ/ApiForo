package alura.foro.foroAPI.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import alura.foro.foroAPI.topico.DatosActualizarTopico;
import alura.foro.foroAPI.topico.DatosListadoRespuestas;
import alura.foro.foroAPI.topico.DatosListadoTopicos;
import alura.foro.foroAPI.topico.DatosRegistroTopico;
import alura.foro.foroAPI.topico.DatosRespuesta;
import alura.foro.foroAPI.topico.DatosRespuestaTopico;
import alura.foro.foroAPI.topico.DatosRespuestaTopicoPorId;
import alura.foro.foroAPI.topico.Respuesta;
import alura.foro.foroAPI.topico.RespuestaRepository;
import alura.foro.foroAPI.topico.Topico;
import alura.foro.foroAPI.topico.TopicoRepository;
import alura.foro.foroAPI.topico.TopicoService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
	@Autowired
	private TopicoRepository repository;
	@Autowired
	private RespuestaRepository respuestaRepository;
	@Autowired
	private TopicoService topicoService;

	@PostMapping
	public ResponseEntity<DatosRespuestaTopico> publicarTopico(
			@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {

		DatosRespuestaTopico datosRespuestaTopico = topicoService.crearTopico(datosRegistroTopico);

		URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosRespuestaTopico.id()).toUri();

		return ResponseEntity.created(url).body(datosRespuestaTopico);

	}

	@GetMapping
	public ResponseEntity<Page<DatosListadoTopicos>> listarTopicos(
			@PageableDefault(sort = "Creacion") Pageable paginacion) {
		Page<Topico> topicosPage = repository.findAll(paginacion);

		Page<DatosListadoTopicos> datosListadoTopicosPage = topicosPage.map(topico -> {
			List<Respuesta> respuestas = respuestaRepository.findBytopico_Id(topico.getId());
			return new DatosListadoTopicos(topico, DatosListadoRespuestas.fromRespuestas(respuestas));
		});

		return ResponseEntity.ok(datosListadoTopicosPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaTopicoPorId> obtenerTopicoPorId(@PathVariable Long id) {

		topicoService.verificarExistenciadelTopico(id);

		Topico topico = repository.getReferenceById(id);
		List<Respuesta> respuestas = respuestaRepository.findBytopico_Id(topico.getId());
		return ResponseEntity
				.ok(new DatosRespuestaTopicoPorId(topico, DatosListadoRespuestas.fromRespuestas(respuestas)));

	}

	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
			@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

		topicoService.verificarExistenciadelTopico(datosActualizarTopico.id());

		Topico topico = repository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarTopico(datosActualizarTopico);
		DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
				topico.getMensaje(), topico.getCreacion(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

		return ResponseEntity.ok(datosRespuestaTopico);

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {

		topicoService.verificarExistenciadelTopico(id);

		Topico topico = repository.getReferenceById(id);
		respuestaRepository.deleteByTopico(id);
		repository.delete(topico);

		return ResponseEntity.ok("Topico eliminado Correctamente");

	}

	@PostMapping(value = "respuesta")
	public ResponseEntity<DatosRespuesta> responderTopico(@RequestBody @Valid DatosRespuesta datosRespuesta) {

		topicoService.verificarExistenciadelTopico(datosRespuesta.topicoid());

		Topico topico = repository.getReferenceById(datosRespuesta.topicoid());
		Respuesta respuesta = new Respuesta(datosRespuesta, topico);
		respuestaRepository.save(respuesta);

		return ResponseEntity.ok(datosRespuesta);

	}

}
