package tiendatech2.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendatech2.model.Producto;
import tiendatech2.repository.ProductoRepository;
import tiendatech2.service.ProductoService;
 
import java.math.BigDecimal;
import java.util.List;
 
@Service
public class ProductoServiceImpl implements ProductoService {
 
    @Autowired
    private ProductoRepository repo;
 
    @Override
    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }
 
    @Override
    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
 
    @Override
    public List<Producto> listarTodos() {
        return repo.findAll();
    }
 
    @Override
    public List<Producto> listarActivos() {
        return repo.findByActivoTrue();
    }
 
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
 
    @Override
    public List<Producto> buscarPorMarca(String marca) {
        return repo.findByMarcaContainingIgnoreCase(marca);
    }
 
    @Override
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return repo.findByCategoriaId(categoriaId);
    }
 
    @Override
    public List<Producto> buscarPorPrecio(BigDecimal min, BigDecimal max) {
        return repo.findByPrecioBetween(min, max);
    }
 
    @Override
    public List<Producto> buscarProductos(String busqueda) {
        return repo.buscarProductos(busqueda);
    }
 
    @Override
    public List<Producto> findProductosConStockBajo() {
        return repo.findProductosConStockBajo();
    }
 
    @Override
    public void actualizarStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setStock(producto.getStock() - cantidad);
            repo.save(producto);
        }
    }
 
    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}