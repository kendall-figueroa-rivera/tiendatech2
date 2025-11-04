package tiendatech2.service;

import tiendatech2.model.Comentario;
import java.util.List;

public interface ComentarioService {
    Comentario guardar(Comentario comentario);
    List<Comentario> buscarPorProducto(Long productoId);
    void eliminar(Long id);
}

