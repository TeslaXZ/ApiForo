package alura.foro.foroAPI.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByLogin(String username);

	boolean existsBylogin(String login);

	boolean existsByclave(String clave);

	boolean existsBycorreo(String correo);
	
}
