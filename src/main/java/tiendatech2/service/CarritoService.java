package tiendatech2.service;

import tiendatech2.model.Carrito;
import java.util.List;

public interface CarritoService {
    Carrito agregarAlCarrito(Long usuarioId, Long productoId, Integer cantidad);
    List<Carrito> obtenerCarrito(Long usuarioId);
    void eliminarDelCarrito(Long carritoId);
    void actualizarCantidad(Long carritoId, Integer cantidad);
    void limpiarCarrito(Long usuarioId);
    Carrito buscarPorId(Long id);
}

