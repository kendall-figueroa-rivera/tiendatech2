package tiendatech2.service;

import tiendatech2.model.Resena;
import java.util.List;

public interface ResenaService {
    Resena guardar(Resena resena);
    Resena buscarPorId(Long id);
    List<Resena> buscarPorProducto(Long productoId);
    List<Resena> buscarPorUsuario(Long usuarioId);
    Double calcularPromedioCalificacion(Long productoId);
    Long contarResenas(Long productoId);
    boolean usuarioYaCalifico(Long usuarioId, Long productoId);
}

