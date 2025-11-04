package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Carrito;
import tiendatech2.model.Usuario;
import tiendatech2.service.CarritoService;
import tiendatech2.service.UsuarioService;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String verCarrito(Authentication authentication, Model model) {
        // Para pruebas sin autenticación
        if (authentication == null) {
            model.addAttribute("items", java.util.Collections.emptyList());
            model.addAttribute("subtotal", BigDecimal.ZERO);
            model.addAttribute("impuestos", BigDecimal.ZERO);
            model.addAttribute("total", BigDecimal.ZERO);
            return "carrito/ver";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            model.addAttribute("items", java.util.Collections.emptyList());
            model.addAttribute("subtotal", BigDecimal.ZERO);
            model.addAttribute("impuestos", BigDecimal.ZERO);
            model.addAttribute("total", BigDecimal.ZERO);
            return "carrito/ver";
        }

        List<Carrito> items = carritoService.obtenerCarrito(usuario.getId());
        BigDecimal subtotal = items.stream()
                .map(item -> item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal impuestos = subtotal.multiply(new BigDecimal("0.13"));
        BigDecimal total = subtotal.add(impuestos);

        model.addAttribute("items", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("impuestos", impuestos);
        model.addAttribute("total", total);

        return "carrito/ver";
    }

    @PostMapping("/agregar")
    @ResponseBody
    public ResponseEntity<?> agregarAlCarrito(
            @RequestParam Long productoId,
            @RequestParam(defaultValue = "1") Integer cantidad,
            Authentication authentication) {
        
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Carrito carrito = carritoService.agregarAlCarrito(usuario.getId(), productoId, cantidad);
        if (carrito != null) {
            return ResponseEntity.ok().body("Producto agregado al carrito");
        } else {
            return ResponseEntity.badRequest().body("Error al agregar producto");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarDelCarrito(@PathVariable Long id) {
        carritoService.eliminarDelCarrito(id);
        return ResponseEntity.ok().body("Producto eliminado del carrito");
    }

    @PutMapping("/{id}/cantidad")
    @ResponseBody
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long id, @RequestParam Integer cantidad) {
        carritoService.actualizarCantidad(id, cantidad);
        return ResponseEntity.ok().body("Cantidad actualizada");
    }
}

