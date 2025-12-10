package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Cupon;
import java.util.Optional;
import java.util.List;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCodigo(String codigo);
    List<Cupon> findByActivoTrue();
}

