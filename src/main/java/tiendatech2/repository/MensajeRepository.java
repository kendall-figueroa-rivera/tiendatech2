package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Mensaje;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByChatIdOrderByFechaEnvioAsc(Long chatId);
    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId ORDER BY m.fechaEnvio ASC")
    List<Mensaje> findMensajesPorChat(@Param("chatId") Long chatId);
}

