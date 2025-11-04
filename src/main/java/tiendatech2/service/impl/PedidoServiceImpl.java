package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendatech2.model.*;
import tiendatech2.repository.*;
import tiendatech2.service.PedidoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Pedido> buscarPorUsuario(Long usuarioId) {
        return pedidoRepository.findPedidosPorUsuario(usuarioId);
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaPedidoDesc();
    }

    @Override
    @Transactional
    public void actualizarEstado(Long pedidoId, Pedido.EstadoPedido estado) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoRepository.save(pedido);
        }
    }

    @Override
    @Transactional
    public Pedido crearPedidoDesdeCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) return null;

        List<Carrito> itemsCarrito = carritoRepository.findCarritoPorUsuario(usuarioId);
        if (itemsCarrito.isEmpty()) return null;

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEstado(Pedido.EstadoPedido.EN_PROCESO);
        pedido.setDireccionEntrega(usuario.getDireccion());

        List<ItemPedido> itemsPedido = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Carrito item : itemsCarrito) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProducto(item.getProducto());
            itemPedido.setCantidad(item.getCantidad());
            itemPedido.setPrecioUnitario(item.getPrecioUnitario());
            itemPedido.setSubtotal(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
            itemsPedido.add(itemPedido);
            subtotal = subtotal.add(itemPedido.getSubtotal());
        }

        pedido.setItems(itemsPedido);

        BigDecimal impuestos = subtotal.multiply(new BigDecimal("0.13")); // 13% de impuestos
        BigDecimal total = subtotal.add(impuestos);

        pedido.setSubtotal(subtotal);
        pedido.setImpuestos(impuestos);
        pedido.setTotal(total);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Limpiar carrito
        carritoRepository.deleteByUsuarioId(usuarioId);

        return pedidoGuardado;
    }
}

