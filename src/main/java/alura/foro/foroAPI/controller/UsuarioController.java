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
import alura.foro.foroAPI.user.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key") 
public class UsuarioController {
	
	
	
	private final AuthService authService;
	private final UsuarioService usuarioService;
	
	@PostMapping(value = "login")
	public ResponseEntity<DatosJWTToken> login(@RequestBody @Valid DatosLoginUsuario datosLoginUsuario) {
		
		usuarioService.verificarExistenciadelUsuarioAlIngresar(datosLoginUsuario.login(), datosLoginUsuario.clave());
		
		return ResponseEntity.ok(authService.login(datosLoginUsuario));
	}
	
	@PostMapping (value = "registro")
	public ResponseEntity<DatosJWTToken> registrar(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
		usuarioService.verificarExistenciadelUsuarioAlregistrarse(datosRegistroUsuario.login(), datosRegistroUsuario.correo());
		return ResponseEntity.ok(authService.registrar(datosRegistroUsuario));
	}

}
