package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.PuntosUsuario;
import java.util.List;

@Repository
public interface PuntosUsuarioRepository extends JpaRepository<PuntosUsuario, Long> {
    List<PuntosUsuario> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);
    @Query("SELECT COALESCE(SUM(p.puntos), 0) FROM PuntosUsuario p WHERE p.usuario.id = :usuarioId")
    Integer calcularPuntosTotales(@Param("usuarioId") Long usuarioId);
}

