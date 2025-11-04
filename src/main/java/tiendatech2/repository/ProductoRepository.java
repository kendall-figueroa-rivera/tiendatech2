package tiendatech2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Producto;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByMarca(String marca);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByMarcaContainingIgnoreCase(String marca);
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio AND p.activo = true")
    List<Producto> findByPrecioBetween(@Param("minPrecio") java.math.BigDecimal minPrecio, @Param("maxPrecio") java.math.BigDecimal maxPrecio);
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:busqueda% OR p.marca LIKE %:busqueda% OR p.descripcion LIKE %:busqueda%")
    List<Producto> buscarProductos(@Param("busqueda") String busqueda);
}

