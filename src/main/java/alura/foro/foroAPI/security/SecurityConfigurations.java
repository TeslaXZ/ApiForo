package alura.foro.foroAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
/**
 * Configuración de seguridad para la aplicación.
 * Configura la seguridad de la aplicación mediante Spring Security, definiendo reglas de autorización y autenticación.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

	private final AuthenticationProvider authProvider;
	private final SecurityFilter securityFilter;
	
	 /**
     * Configura la cadena de filtros de seguridad para las peticiones HTTP.
     *
     * @param http La configuración de HttpSecurity.
     * @return La cadena de filtros de seguridad.
     * @throws Exception Si hay un error durante la configuración.
     */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authRequest -> authRequest.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(
						sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

}
