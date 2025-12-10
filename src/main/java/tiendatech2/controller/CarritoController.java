package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @PostMapping("/agregar")
    @ResponseBody
    public ResponseEntity<?> agregar(@RequestParam("productoId") Long productoId,
                                     @RequestParam(value = "cantidad", defaultValue = "1") Integer cantidad,
                                     Authentication authentication,
                                     RedirectAttributes ra) {
        if (productoId == null || cantidad == null || cantidad <= 0) {
            return ResponseEntity.badRequest().body("Producto o cantidad inválida");
        }

        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión para agregar productos al carrito");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Carrito carrito = carritoService.agregarAlCarrito(usuario.getId(), productoId, cantidad);
        if (carrito != null) {
            return ResponseEntity.ok().body("Producto agregado al carrito");
        } else {
            return ResponseEntity.badRequest().body("Error al agregar producto al carrito");
        }
    }

    @GetMapping
    public String ver(Authentication authentication, Model model) {
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

    @PutMapping("/{id}/cantidad")
    @ResponseBody
    public ResponseEntity<?> actualizarCantidad(@PathVariable Long id,
                                                 @RequestParam Integer cantidad,
                                                 Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        if (cantidad == null || cantidad <= 0) {
            return ResponseEntity.badRequest().body("Cantidad inválida");
        }

        Carrito carrito = carritoService.buscarPorId(id);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null || !carrito.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body("No autorizado");
        }

        carritoService.actualizarCantidad(id, cantidad);
        return ResponseEntity.ok().body("Cantidad actualizada");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminar(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        Carrito carrito = carritoService.buscarPorId(id);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null || !carrito.getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.status(403).body("No autorizado");
        }

        carritoService.eliminarDelCarrito(id);
        return ResponseEntity.ok().body("Producto eliminado del carrito");
    }
}
