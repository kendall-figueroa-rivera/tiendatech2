package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendatech2.model.Favorito;
import tiendatech2.model.Producto;
import tiendatech2.model.Usuario;
import tiendatech2.repository.FavoritoRepository;
import tiendatech2.repository.ProductoRepository;
import tiendatech2.repository.UsuarioRepository;
import tiendatech2.service.FavoritoService;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Favorito agregarFavorito(Long usuarioId, Long productoId) {
        Optional<Favorito> favoritoExistente = favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
        if (favoritoExistente.isPresent()) {
            return favoritoExistente.get();
        }

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Producto producto = productoRepository.findById(productoId).orElse(null);
        
        if (usuario == null || producto == null) return null;

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);
        return favoritoRepository.save(favorito);
    }

    @Override
    @Transactional
    public void eliminarFavorito(Long favoritoId) {
        favoritoRepository.deleteById(favoritoId);
    }

    @Override
    public List<Favorito> obtenerFavoritos(Long usuarioId) {
        return favoritoRepository.findFavoritosPorUsuario(usuarioId);
    }

    @Override
    public boolean esFavorito(Long usuarioId, Long productoId) {
        return favoritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId).isPresent();
    }
}

