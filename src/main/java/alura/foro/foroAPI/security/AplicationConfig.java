package alura.foro.foroAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import alura.foro.foroAPI.user.UsuarioRepository;

/**
 * Configuración de la aplicación para la seguridad y autenticación de usuarios.
 * Define beans para la gestión de autenticación, proveedor de autenticación, codificador de contraseñas y servicio de detalles del usuario.
 * Utiliza Spring Security para la configuración de seguridad.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */


@Configuration
@RequiredArgsConstructor
public class AplicationConfig {
	
	private final UsuarioRepository repository;
	
	 /**
     * Bean para proporcionar el AuthenticationManager para la autenticación de usuarios.
     *
     * @param authenticationConfiguration La configuración de autenticación.
     * @return El AuthenticationManager para la autenticación de usuarios.
     * @throws Exception Si hay un error al obtener el AuthenticationManager.
     */
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();

	}
	
	/**
     * Bean para proporcionar el AuthenticationProvider para la autenticación de usuarios.
     *
     * @return El AuthenticationProvider para la autenticación de usuarios.
     */
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
	
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService());
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
	
	   /**
     * Bean para proporcionar el codificador de contraseñas.
     *
     * @return El PasswordEncoder para codificar contraseñas.
     */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	 /**
     * Bean para proporcionar el UserDetailsService para obtener detalles del usuario.
     *
     * @return El UserDetailsService para obtener detalles del usuario.
     */
	@Bean
	public UserDetailsService userDetailService() {
		
		return username -> repository.findByLogin(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found"));	
	}
	
}
