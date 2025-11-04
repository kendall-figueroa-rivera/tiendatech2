package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendatech2.model.Carrito;
import tiendatech2.model.Producto;
import tiendatech2.model.Usuario;
import tiendatech2.repository.CarritoRepository;
import tiendatech2.repository.ProductoRepository;
import tiendatech2.repository.UsuarioRepository;
import tiendatech2.service.CarritoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Carrito agregarAlCarrito(Long usuarioId, Long productoId, Integer cantidad) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Producto producto = productoRepository.findById(productoId).orElse(null);
        
        if (usuario == null || producto == null) return null;

        Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
        
        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            return carritoRepository.save(carrito);
        } else {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(usuario);
            nuevoCarrito.setProducto(producto);
            nuevoCarrito.setCantidad(cantidad);
            nuevoCarrito.setPrecioUnitario(producto.getPrecio());
            return carritoRepository.save(nuevoCarrito);
        }
    }

    @Override
    public List<Carrito> obtenerCarrito(Long usuarioId) {
        return carritoRepository.findCarritoPorUsuario(usuarioId);
    }

    @Override
    @Transactional
    public void eliminarDelCarrito(Long carritoId) {
        carritoRepository.deleteById(carritoId);
    }

    @Override
    @Transactional
    public void actualizarCantidad(Long carritoId, Integer cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId).orElse(null);
        if (carrito != null) {
            carrito.setCantidad(cantidad);
            carritoRepository.save(carrito);
        }
    }

    @Override
    @Transactional
    public void limpiarCarrito(Long usuarioId) {
        carritoRepository.deleteByUsuarioId(usuarioId);
    }

    @Override
    public Carrito buscarPorId(Long id) {
        return carritoRepository.findById(id).orElse(null);
    }
}

