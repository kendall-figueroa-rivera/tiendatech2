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
    private ProductoRepository productoRepository;

    @Override
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> buscarPorMarca(String marca) {
        return productoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    @Override
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Override
    public List<Producto> buscarPorPrecio(BigDecimal minPrecio, BigDecimal maxPrecio) {
        return productoRepository.findByPrecioBetween(minPrecio, maxPrecio);
    }

    @Override
    public List<Producto> buscarProductos(String busqueda) {
        return productoRepository.buscarProductos(busqueda);
    }

    @Override
    public List<Producto> findProductosConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    @Override
    public void actualizarStock(Long productoId, Integer cantidad) {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto != null) {
            producto.setStock(cantidad);
            productoRepository.save(producto);
        }
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setActivo(false);
            productoRepository.save(producto);
        }
    }
}

