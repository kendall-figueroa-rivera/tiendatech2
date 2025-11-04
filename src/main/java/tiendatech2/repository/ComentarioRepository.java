package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Comentario;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByProductoIdOrderByFechaComentarioDesc(Long productoId);
    @Query("SELECT c FROM Comentario c WHERE c.producto.id = :productoId ORDER BY c.fechaComentario DESC")
    List<Comentario> findComentariosPorProducto(@Param("productoId") Long productoId);
}

