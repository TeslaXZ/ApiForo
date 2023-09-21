package alura.foro.foroAPI.topico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Clase que representa un tema de discusión en el foro.
 * Contiene información sobre el título, mensaje, fecha de creación, estado, autor y respuestas asociadas al tema.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */


@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;

	private LocalDate creacion;
	
	@Enumerated(EnumType.STRING)
	private EstatusTopico estatus;
	
	private String autor;
	
	@Enumerated(EnumType.STRING)
	private Curso curso;
	
	@OneToMany(mappedBy = "topico",cascade = CascadeType.ALL)
	private List<Respuesta> respuestas = new ArrayList<>(); 

	/**
     * Constructor para crear un nuevo tema a partir de datos de registro.
     *
     * @param datosRegistroTopico Datos de registro del tema.
     */
	public Topico(DatosRegistroTopico datosRegistroTopico) {
		this.titulo = datosRegistroTopico.titulo();
		this.mensaje = datosRegistroTopico.mensaje();
		this.creacion = LocalDate.now();
		this.estatus = EstatusTopico.NO_SOLUCIONADO;
		this.autor = datosRegistroTopico.autor();
		this.curso = datosRegistroTopico.curso();

	}
	 /**
     * Actualiza la información del tema con los datos proporcionados.
     *
     * @param actualizarTopico Datos actualizados del tema.
     */
	public void actualizarTopico(DatosActualizarTopico actualizarTopico) {
		this.titulo = actualizarTopico.titulo();
		this.mensaje = actualizarTopico.mensaje();
		this.curso = actualizarTopico.curso();
		
		
	}
	/**
     * Marca el tema como solucionado.
     */
	public void marcarComoSolucionado() {
		this.estatus = EstatusTopico.SOLUCIONADO;
		
	}

	

}
