package alura.foro.foroAPI.topico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
/**
 * Interfaz que define un repositorio de datos para operaciones relacionadas con respuestas en el foro.
 * Proporciona métodos para acceder y manipular información de las respuestas almacenadas.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	
	  /**
     * Obtiene una lista de respuestas por el ID del tema al que están asociadas.
     *
     * @param id ID del tema.
     * @return Lista de respuestas asociadas al tema con el ID dado.
     */
	List<Respuesta> findBytopico_Id(Long id);
	
	  /**
     * Elimina todas las respuestas asociadas a un tema por su ID.
     *
     * @param topicoId ID del tema.
     */
	
	@Query("""
			DELETE FROM Respuesta r WHERE r.topico.id = :topicoId
			""")
	@Modifying
	void deleteByTopico(Long topicoId);


}
