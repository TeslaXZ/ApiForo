package alura.foro.foroAPI.topico;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Clase que representa una respuesta a un topico.
 * Contiene información sobre el usuario que responde, el contenido de la respuesta, la fecha de respuesta y el tema al que pertenece.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */
@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String usuario;
	private String respuesta;
	private LocalDate fecha;
	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;
	 /**
     * Constructor para crear una nueva respuesta a partir de datos de respuesta y un tema asociado.
     *
     * @param datosRespuesta Datos de la respuesta.
     * @param topico Tema al que pertenece la respuesta.
     */	
	
	public Respuesta(DatosRespuesta datosRespuesta, Topico topico) {
		this.usuario = datosRespuesta.usuario();
		this.respuesta = datosRespuesta.respuesta();
		this.fecha = LocalDate.now();
		this.topico = topico;
	}
	
	
}
