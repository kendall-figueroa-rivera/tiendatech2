package tiendatech2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Producto;
import tiendatech2.service.ProductoService;
import java.util.List;

@RestController
@RequestMapping("/api/busqueda")
public class BusquedaController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> buscar(@RequestParam String q) {
        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.ok(productoService.listarActivos());
        }
        List<Producto> resultados = productoService.buscarProductos(q);
        return ResponseEntity.ok(resultados);
    }
}

