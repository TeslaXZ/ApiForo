package alura.foro.foroAPI.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Servicio para generar y validar tokens JWT (JSON Web Tokens).
 * Proporciona métodos para generar tokens JWT, extraer información de tokens y validar su autenticidad.
 *
 * @version 1.0
 * @since 2023-09-21
 * @author Brian Diaz
 */

@Service
public class JwtService {

	private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

	
	
	public String getToken(UserDetails usuario) {
		return getToken(new HashMap<>(), usuario);

	}
	 /**
     * Genera un token JWT para el usuario con los claims adicionales especificados.
     *
     * @param extraClaims Los claims adicionales para incluir en el token.
     * @param usuario El UserDetails del usuario para el que se genera el token.
     * @return El token JWT generado.
     */
	private String getToken(Map<String, Object> extraClaims, UserDetails usuario) {
		return Jwts.builder().setClaims(extraClaims).setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getKey(), SignatureAlgorithm.HS256).compact();

	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	 /**
     * Obtiene el nombre de usuario (subject) almacenado en el token JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario almacenado en el token.
     */
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	  /**
     * Verifica si un token JWT es válido para un UserDetails dado.
     *
     * @param token El token JWT.
     * @param userDetails Los detalles del usuario para verificar.
     * @return true si el token es válido para el UserDetails dado, false de lo contrario.
     */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}
	 /**
     * Obtiene todos los claims almacenados en el token JWT.
     *
     * @param token El token JWT.
     * @return Los claims almacenados en el token.
     */
	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}
	  /**
     * Obtiene un claim específico del token JWT.
     *
     * @param token El token JWT.
     * @param claimsResolver La función para obtener el claim específico.
     * @param <T> El tipo del claim.
     * @return El claim específico obtenido del token.
     */
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);

	}
	 /**
     * Obtiene la fecha de expiración del token JWT.
     *
     * @param token El token JWT.
     * @return La fecha de expiración del token.
     */	
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
	/**
     * Verifica si un token JWT ha expirado.
     *
     * @param token El token JWT.
     * @return true si el token ha expirado, false de lo contrario.
     */
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

}
