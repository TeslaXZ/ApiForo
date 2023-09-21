package alura.foro.foroAPI.errors;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class ErrorManager {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarError404(EntityNotFoundException e) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
		var errores = e.getFieldErrors().stream().map(DatosErrorDeValidacion::new).toList();
		return ResponseEntity.badRequest().body(errores);
	}

	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity errorHandlerValidacionesDeIntegridad(Exception e) {
		
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	private record DatosErrorDeValidacion(String campo, String error) {
		public DatosErrorDeValidacion(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
}
