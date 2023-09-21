package alura.foro.foroAPI.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long>{

	boolean existsBytitulo(String titulo);

	

}
