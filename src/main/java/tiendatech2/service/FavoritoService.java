package tiendatech2.service;

import tiendatech2.model.Favorito;
import java.util.List;

public interface FavoritoService {
    Favorito agregarFavorito(Long usuarioId, Long productoId);
    void eliminarFavorito(Long favoritoId);
    List<Favorito> obtenerFavoritos(Long usuarioId);
    boolean esFavorito(Long usuarioId, Long productoId);
}

