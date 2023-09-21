package alura.foro.foroAPI.errors;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

/**
 * Manejador global de errores para la API del foro.
 * Proporciona métodos para manejar errores específicos y devolver respuestas adecuadas.
 *
 * @author Brian Diaz
 * @version 1.0
 * @since 2023-09-21
 */

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class ErrorManager {
	
	 /**
     * Maneja el error de entidad no encontrada (HTTP 404 - Not Found).
     *
     * @param e La excepción de entidad no encontrada.
     * @return ResponseEntity con la respuesta HTTP 404.
     */
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarError404(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}
	
	 /**
     * Maneja el error de argumento no válido (HTTP 400 - Bad Request) debido a validación.
     *
     * @param e La excepción de argumento no válido.
     * @return ResponseEntity con los errores de validación.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
		var errores = e.getFieldErrors().stream().map(DatosErrorDeValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}

	 /**
     * Maneja las excepciones de validación de integridad (HTTP 400 - Bad Request).
     *
     * @param e La excepción de validación de integridad.
     * @return ResponseEntity con el mensaje de error de validación.
     */
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e) {
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
}
