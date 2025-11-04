package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Oferta;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    @Query("SELECT o FROM Oferta o WHERE o.activa = true AND o.fechaInicio <= :fecha AND o.fechaFin >= :fecha")
    List<Oferta> findOfertasActivas(@Param("fecha") Timestamp fecha);
    List<Oferta> findByActivaTrue();
    @Query("SELECT o FROM Oferta o WHERE o.producto.id = :productoId AND o.activa = true AND o.fechaInicio <= :fecha AND o.fechaFin >= :fecha")
    Optional<Oferta> findOfertaActivaPorProducto(@Param("productoId") Long productoId, @Param("fecha") Timestamp fecha);
    java.util.Optional<Oferta> findByProductoId(Long productoId);
}

