package alura.foro.foroAPI.topico;


import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(@NotNull Long id, String titulo, String mensaje, Curso curso) {

}
