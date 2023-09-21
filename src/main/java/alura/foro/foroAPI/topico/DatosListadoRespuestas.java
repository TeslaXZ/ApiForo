package alura.foro.foroAPI.topico;
import java.time.LocalDate;
import java.util.List;

public record DatosListadoRespuestas(String usuario, String respuesta, LocalDate fecha) {


	public static List<DatosListadoRespuestas> fromRespuestas(List<Respuesta> respuestas) {
        return respuestas.stream()
                .map(respuesta -> new DatosListadoRespuestas(
                        respuesta.getUsuario(), respuesta.getRespuesta(), respuesta.getFecha()))
                .toList();
    }
}
