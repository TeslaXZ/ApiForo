package alura.foro.foroAPI.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alura.foro.foroAPI.user.DatosLoginUsuario;
import alura.foro.foroAPI.user.DatosRegistroUsuario;
import alura.foro.foroAPI.user.Rol;
import alura.foro.foroAPI.user.Usuario;
import alura.foro.foroAPI.user.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UsuarioRepository repository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public DatosJWTToken login(DatosLoginUsuario datosLoginUsuario) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(datosLoginUsuario.login(), datosLoginUsuario.clave()));
				UserDetails usuario = repository.findByLogin(datosLoginUsuario.login()).orElseThrow();
				String token = jwtService.getToken(usuario);
				
				
		return new DatosJWTToken(token);
	}

	public DatosJWTToken registrar(DatosRegistroUsuario datosRegistroUsuario) {
		Usuario usuario = Usuario.builder().nombre(datosRegistroUsuario.nombre()).correo(datosRegistroUsuario.correo())
				.login(datosRegistroUsuario.login()).clave(passwordEncoder.encode(datosRegistroUsuario.clave()))
				.rol(Rol.USER).build();

		repository.save(usuario);

		return new DatosJWTToken(jwtService.getToken(usuario));

	}

}