package tiendatech2.service;

import tiendatech2.model.Producto;
import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    Producto guardar(Producto producto);
    Producto buscarPorId(Long id);
    List<Producto> listarTodos();
    List<Producto> listarActivos();
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorMarca(String marca);
    List<Producto> buscarPorCategoria(Long categoriaId);
    List<Producto> buscarPorPrecio(BigDecimal minPrecio, BigDecimal maxPrecio);
    List<Producto> buscarProductos(String busqueda);
    List<Producto> buscarConFiltros(String busqueda, Long categoria, String marca, BigDecimal minPrecio, BigDecimal maxPrecio);
    List<Producto> ordenarProductos(List<Producto> productos, String ordenarPor, String direccion);
    List<String> obtenerMarcasUnicas();
    List<Producto> findProductosConStockBajo();
    void actualizarStock(Long productoId, Integer cantidad);
    void eliminar(Long id);
}

