package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Resena;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProductoIdOrderByFechaCreacionDesc(Long productoId);
    List<Resena> findByUsuarioId(Long usuarioId);
    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.producto.id = :productoId")
    Double calcularPromedioCalificacion(@Param("productoId") Long productoId);
    @Query("SELECT COUNT(r) FROM Resena r WHERE r.producto.id = :productoId")
    Long contarPorProducto(@Param("productoId") Long productoId);
}

