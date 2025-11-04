package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Devolucion;
import java.util.List;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {
    List<Devolucion> findByUsuarioIdOrderByFechaSolicitudDesc(Long usuarioId);
    @Query("SELECT d FROM Devolucion d WHERE d.usuario.id = :usuarioId ORDER BY d.fechaSolicitud DESC")
    List<Devolucion> findDevolucionesPorUsuario(@Param("usuarioId") Long usuarioId);
}

