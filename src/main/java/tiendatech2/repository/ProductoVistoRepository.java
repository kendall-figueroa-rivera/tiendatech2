package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.ProductoVisto;
import java.util.List;

@Repository
public interface ProductoVistoRepository extends JpaRepository<ProductoVisto, Long> {
    List<ProductoVisto> findByUsuarioIdOrderByFechaVistoDesc(Long usuarioId);
    List<ProductoVisto> findBySessionIdOrderByFechaVistoDesc(String sessionId);
    @Query("SELECT DISTINCT pv.producto FROM ProductoVisto pv WHERE pv.usuario.id = :usuarioId ORDER BY pv.fechaVisto DESC")
    List<tiendatech2.model.Producto> findProductosVistosPorUsuario(@Param("usuarioId") Long usuarioId);
}

