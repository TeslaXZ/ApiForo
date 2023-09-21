package alura.foro.foroAPI.topico;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz que define un repositorio de datos para operaciones relacionadas con temas en el foro.
 * Proporciona métodos para acceder y manipular información de los temas almacenados.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */
public interface TopicoRepository extends JpaRepository<Topico, Long>{
	
	/**
     * Verifica si existe un tema con el título proporcionado.
     *
     * @param titulo Título del tema a verificar.
     * @return true si existe un tema con el título dado, false de lo contrario.
     */
	boolean existsBytitulo(String titulo);

	

}
