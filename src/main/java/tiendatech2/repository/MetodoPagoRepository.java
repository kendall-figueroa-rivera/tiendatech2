package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tiendatech2.model.MetodoPago;
import java.util.List;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    List<MetodoPago> findByActivoTrue();
}

