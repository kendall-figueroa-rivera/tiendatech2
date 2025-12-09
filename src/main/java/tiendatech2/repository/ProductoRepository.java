package tiendatech2.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tiendatech2.model.Producto;
import java.util.List;
 
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
 
    List<Producto> findByActivoTrue();
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByMarcaContainingIgnoreCase(String marca);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
 
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :min AND :max AND p.activo = true")
    List<Producto> findByPrecioBetween(@Param("min") java.math.BigDecimal minPrecio,
                                       @Param("max") java.math.BigDecimal maxPrecio);
 
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
 
    @Query("""
            SELECT p FROM Producto p 
            WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%'))
               OR LOWER(p.marca) LIKE LOWER(CONCAT('%', :busqueda, '%'))
               OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :busqueda, '%'))
            """)
    List<Producto> buscarProductos(@Param("busqueda") String busqueda);
}