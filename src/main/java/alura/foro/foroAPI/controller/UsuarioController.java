package alura.foro.foroAPI.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alura.foro.foroAPI.security.AuthService;
import alura.foro.foroAPI.security.DatosJWTToken;
import alura.foro.foroAPI.user.DatosLoginUsuario;
import alura.foro.foroAPI.user.DatosRegistroUsuario;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
	
	
	
	private final AuthService authService;
	
	@PostMapping(value = "login")
	public ResponseEntity<DatosJWTToken> login(@RequestBody DatosLoginUsuario datosLoginUsuario) {
		
		
		return ResponseEntity.ok(authService.login(datosLoginUsuario));
	}
	
	@PostMapping (value = "registro")
	public ResponseEntity<DatosJWTToken> registrar(@RequestBody DatosRegistroUsuario datosRegistroUsuario) {
		return ResponseEntity.ok(authService.registrar(datosRegistroUsuario));
	}

}
