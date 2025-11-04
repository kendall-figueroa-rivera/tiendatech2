package tiendatech2.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Carrito;
import tiendatech2.model.Usuario;
import tiendatech2.service.CarritoService;
import tiendatech2.service.UsuarioService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
public class CarritoApiController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> obtenerCarrito(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        List<Carrito> items = carritoService.obtenerCarrito(usuario.getId());
        BigDecimal subtotal = items.stream()
                .map(item -> item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal impuestos = subtotal.multiply(new BigDecimal("0.13"));
        BigDecimal total = subtotal.add(impuestos);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("items", items);
        respuesta.put("subtotal", subtotal);
        respuesta.put("impuestos", impuestos);
        respuesta.put("total", total);

        return ResponseEntity.ok(respuesta);
    }
}

