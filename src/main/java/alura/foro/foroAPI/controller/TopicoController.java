package alura.foro.foroAPI.controller;

import java.net.URI;

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
import alura.foro.foroAPI.topico.DatosListadoTopicos;
import alura.foro.foroAPI.topico.DatosRegistroTopico;
import alura.foro.foroAPI.topico.DatosRespuestaTopico;
import alura.foro.foroAPI.topico.Topico;
import alura.foro.foroAPI.topico.TopicoRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
	@Autowired
	TopicoRepository repository;

	@PostMapping
	public ResponseEntity<DatosRespuestaTopico> publicarTopico(
			@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {

		Topico topico = repository.save(new Topico(datosRegistroTopico));

		DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
				topico.getMensaje(), topico.getCreacion(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

		System.out.println(topico.getCreacion());

		URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		return ResponseEntity.created(url).body(datosRespuestaTopico);

	}

	@GetMapping
	public ResponseEntity<Page<DatosListadoTopicos>> listarTopicos(
			@PageableDefault(sort = "Creacion") Pageable paginacion) {

		return ResponseEntity.ok(repository.findAll(paginacion).map(DatosListadoTopicos::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaTopico> obtenerTopico(@PathVariable Long id) {

		Topico topico = repository.getReferenceById(id);
		DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
				topico.getMensaje(), topico.getCreacion(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

		return ResponseEntity.ok(datosRespuestaTopico);

	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
			@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

		Topico topico = repository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarTopico(datosActualizarTopico);
		DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),
				topico.getMensaje(), topico.getCreacion(), topico.getEstatus(), topico.getAutor(), topico.getCurso());

		return ResponseEntity.ok(datosRespuestaTopico);

	}
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
		Topico topico = repository.getReferenceById(id);
		repository.delete(topico);
		return ResponseEntity.ok("Topico eliminado Correctamente");
		
		
	}
	

}
