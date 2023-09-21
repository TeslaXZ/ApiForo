package alura.foro.foroAPI.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filtro de seguridad para procesar peticiones y autenticar usuarios utilizando tokens JWT.
 * Extiende OncePerRequestFilter para garantizar que se ejecute solo una vez por petición.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserDetailsService userDetailsService;
	
	 /**
     * Procesa la petición HTTP y aplica la lógica de autenticación y autorización basada en tokens JWT.
     *
     * @param request La solicitud HTTP.
     * @param response La respuesta HTTP.
     * @param filterChain La cadena de filtros.
     * @throws ServletException Si hay un error en el servlet.
     * @throws IOException Si hay un error de entrada o salida.
     */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String token = getTokenFromRequest(request);
		final String username;

		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		username = jwtService.getUsernameFromToken(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtService.isTokenValid(token, userDetails)) {

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

			}

		}

		filterChain.doFilter(request, response);

	}
	 /**
     * Obtiene el token JWT de la solicitud HTTP.
     *
     * @param request La solicitud HTTP.
     * @return El token JWT si está presente en la solicitud, o null si no lo está.
     */

	private String getTokenFromRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

			return authHeader.substring(7);

		}

		return null;
	}

}
