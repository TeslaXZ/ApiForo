package alura.foro.foroAPI.topico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

	List<Respuesta> findBytopico_Id(Long id);
	
	
	
	@Query("""
			DELETE FROM Respuesta r WHERE r.topico.id = :topicoId
			""")
	@Modifying
	void deleteByTopico(Long topicoId);


}
