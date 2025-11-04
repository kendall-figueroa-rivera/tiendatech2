package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Favorito;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioIdOrderByFechaAgregadoDesc(Long usuarioId);
    Optional<Favorito> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId ORDER BY f.fechaAgregado DESC")
    List<Favorito> findFavoritosPorUsuario(@Param("usuarioId") Long usuarioId);
}

