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
	
	
	public Respuesta(DatosRespuesta datosRespuesta, Topico topico) {
		this.usuario = datosRespuesta.usuario();
		this.respuesta = datosRespuesta.respuesta();
		this.fecha = LocalDate.now();
		this.topico = topico;
	}
	
	
}
