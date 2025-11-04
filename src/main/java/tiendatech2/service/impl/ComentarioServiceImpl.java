package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendatech2.model.Comentario;
import tiendatech2.repository.ComentarioRepository;
import tiendatech2.service.ComentarioService;
import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public Comentario guardar(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> buscarPorProducto(Long productoId) {
        return comentarioRepository.findComentariosPorProducto(productoId);
    }

    @Override
    public void eliminar(Long id) {
        comentarioRepository.deleteById(id);
    }
}

