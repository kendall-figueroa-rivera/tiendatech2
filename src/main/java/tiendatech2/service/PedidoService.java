package tiendatech2.service;

import tiendatech2.model.Pedido;
import java.util.List;

public interface PedidoService {
    Pedido guardar(Pedido pedido);
    Pedido buscarPorId(Long id);
    List<Pedido> buscarPorUsuario(Long usuarioId);
    List<Pedido> listarTodos();
    void actualizarEstado(Long pedidoId, Pedido.EstadoPedido estado);
    Pedido crearPedidoDesdeCarrito(Long usuarioId);
}

