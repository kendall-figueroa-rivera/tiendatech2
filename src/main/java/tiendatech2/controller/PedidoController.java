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
    public String crearPedido(@RequestParam Long metodoPagoId, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/auth/login";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        Pedido pedido = pedidoService.crearPedidoDesdeCarrito(usuario.getId());
        if (pedido != null && metodoPagoId != null) {
            pedido.setMetodoPago(metodoPagoService.buscarPorId(metodoPagoId));
            pedidoService.guardar(pedido);
        }

        return "redirect:/pedidos/" + pedido.getId();
    }
}

