package tiendatech2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tiendatech2.model.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}