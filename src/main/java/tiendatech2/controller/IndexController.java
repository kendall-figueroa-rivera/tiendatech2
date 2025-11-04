package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tiendatech2.model.Usuario;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.ProductoService;
import tiendatech2.service.OfertaService;

@Controller
public class IndexController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OfertaService ofertaService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        try {
            // Mensaje de bienvenida personalizado (HU21)
            if (authentication != null) {
                Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
                if (usuario != null) {
                    model.addAttribute("nombreUsuario", usuario.getNombre());
                    model.addAttribute("mensajeBienvenida", "¡Bienvenido, " + usuario.getNombre() + "!");
                }
            }

            // Productos destacados
            try {
                model.addAttribute("productos", productoService.listarActivos());
            } catch (Exception e) {
                model.addAttribute("productos", java.util.Collections.emptyList());
            }

            try {
                model.addAttribute("ofertas", ofertaService.listarActivas());
            } catch (Exception e) {
                model.addAttribute("ofertas", java.util.Collections.emptyList());
            }
        } catch (Exception e) {
            // Si hay algún error, mostrar listas vacías
            model.addAttribute("productos", java.util.Collections.emptyList());
            model.addAttribute("ofertas", java.util.Collections.emptyList());
        }

        return "index";
    }
}

