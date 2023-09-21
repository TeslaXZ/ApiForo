package alura.foro.foroAPI.errors;

import org.springframework.validation.FieldError;

/**
 * Clase de datos para representar errores de validación.
 * 
 * @author Brian Diaz
 * @version 1.0
 * @since 2023-09-21
 */
public record DatosErrorDeValidacion(String campo, String error) {
	  /**
     * Crea una instancia de DatosErrorDeValidacion a partir de un FieldError.
     *
     * @param error El error de campo.
     */
	public DatosErrorDeValidacion(FieldError error) {
		this(error.getField(), error.getDefaultMessage());
	}
	}