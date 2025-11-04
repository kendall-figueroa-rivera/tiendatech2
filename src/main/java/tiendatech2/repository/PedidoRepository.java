package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Pedido;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByFechaPedidoDesc(Long usuarioId);
    List<Pedido> findAllByOrderByFechaPedidoDesc();
    @Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId ORDER BY p.fechaPedido DESC")
    List<Pedido> findPedidosPorUsuario(@Param("usuarioId") Long usuarioId);
}

