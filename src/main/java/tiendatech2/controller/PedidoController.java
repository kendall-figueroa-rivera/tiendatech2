package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Pedido;
import tiendatech2.model.Usuario;
import tiendatech2.service.PedidoService;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.MetodoPagoService;
import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public String verHistorial(Authentication authentication, Model model) {
        // Para pruebas sin autenticación
        if (authentication == null) {
            model.addAttribute("pedidos", java.util.Collections.emptyList());
            return "pedidos/historial";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            model.addAttribute("pedidos", java.util.Collections.emptyList());
            return "pedidos/historial";
        }

        List<Pedido> pedidos = pedidoService.buscarPorUsuario(usuario.getId());
        model.addAttribute("pedidos", pedidos);

        return "pedidos/historial";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Authentication authentication, Model model) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            return "redirect:/pedidos";
        }

        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
            if (usuario != null && !pedido.getUsuario().getId().equals(usuario.getId()) && 
                !usuario.getRol().getNombre().equals("ADMIN")) {
                return "redirect:/pedidos";
            }
        }

        model.addAttribute("pedido", pedido);
        return "pedidos/detalle";
    }

    @PostMapping("/crear")
    public String crearPedido(@RequestParam Long metodoPagoId, Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/auth/login";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        try {
            Pedido pedido = pedidoService.crearPedidoDesdeCarrito(usuario.getId());
            if (pedido == null) {
                model.addAttribute("error", "El carrito está vacío");
                return "redirect:/carrito?error=carrito_vacio";
            }

            if (metodoPagoId != null) {
                pedido.setMetodoPago(metodoPagoService.buscarPorId(metodoPagoId));
                pedidoService.guardar(pedido);
            }

            return "redirect:/pedidos/confirmacion/" + pedido.getId();
        } catch (RuntimeException e) {
            // Error de stock u otro error
            model.addAttribute("error", e.getMessage());
            return "redirect:/carrito?error=" + java.net.URLEncoder.encode(e.getMessage(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar el pedido: " + e.getMessage());
            return "redirect:/carrito?error=error_generico";
        }
    }

    @GetMapping("/confirmacion/{id}")
    public String confirmacionPago(@PathVariable Long id, Authentication authentication, Model model) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            return "redirect:/pedidos";
        }

        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
            if (usuario != null && !pedido.getUsuario().getId().equals(usuario.getId()) && 
                !usuario.getRol().getNombre().equals("ROLE_ADMIN")) {
                return "redirect:/pedidos";
            }
        }

        model.addAttribute("pedido", pedido);
        return "pedidos/confirmacion";
    }

    @GetMapping("/{id}/factura")
    public void descargarFactura(@PathVariable Long id, Authentication authentication, 
                                 jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (pedido == null) {
            response.sendError(jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
            if (usuario != null && !pedido.getUsuario().getId().equals(usuario.getId()) && 
                !usuario.getRol().getNombre().equals("ROLE_ADMIN")) {
                response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        // Generar PDF (por ahora HTML simple)
        java.io.ByteArrayOutputStream baos = pdfService.generarFactura(pedido);
        
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-Disposition", "inline; filename=factura_" + pedido.getId() + ".html");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
    }

    @Autowired
    private tiendatech2.service.PdfService pdfService;
}

