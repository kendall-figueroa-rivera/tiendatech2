package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Carrito;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(Long usuarioId);
    Optional<Carrito> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
    void deleteByUsuarioId(Long usuarioId);
    @Query("SELECT c FROM Carrito c WHERE c.usuario.id = :usuarioId")
    List<Carrito> findCarritoPorUsuario(@Param("usuarioId") Long usuarioId);
}

