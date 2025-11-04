package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Chat;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUsuarioIdOrderByFechaInicioDesc(Long usuarioId);
    Optional<Chat> findByUsuarioIdAndEstado(Long usuarioId, tiendatech2.model.Chat.EstadoChat estado);
    @Query("SELECT c FROM Chat c WHERE c.usuario.id = :usuarioId AND c.estado = 'ABIERTO' ORDER BY c.fechaInicio DESC")
    List<Chat> findChatsAbiertosPorUsuario(@Param("usuarioId") Long usuarioId);
}

