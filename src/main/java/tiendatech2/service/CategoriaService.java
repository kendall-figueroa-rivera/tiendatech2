package tiendatech2.service;

import tiendatech2.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Categoria guardar(Categoria categoria);
    Categoria buscarPorId(Long id);
    Categoria buscarPorNombre(String nombre);
    List<Categoria> listarTodas();
    List<Categoria> listarActivas();
    void eliminar(Long id);
}

