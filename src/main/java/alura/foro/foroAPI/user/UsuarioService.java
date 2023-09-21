package alura.foro.foroAPI.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository repository;

	public void verificarExistenciadelUsuarioAlregistrarse(String login, String correo) {

		if (repository.existsBylogin(login)) {
			throw new ValidationException("El nombre "+ login + " de usuario ya se encuentra en uso");
		}
		
		if(repository.existsBycorreo(correo)) {
			throw new ValidationException("Este correo ya se encuentra en uso");
		}

	}
	
	public void verificarExistenciadelUsuarioAlIngresar(String login, String clave) {

		if (!repository.existsBylogin(login) && !repository.existsByclave(clave)) {
			throw new ValidationException("Usuario y/o contraseña incorrectos");
		}

	}
	
	
}
