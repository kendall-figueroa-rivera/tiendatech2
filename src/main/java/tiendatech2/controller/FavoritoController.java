package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Favorito;
import tiendatech2.model.Usuario;
import tiendatech2.service.FavoritoService;
import tiendatech2.service.UsuarioService;
import java.util.List;

@Controller
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String verFavoritos(Authentication authentication, Model model) {
        // Para pruebas sin autenticación
        if (authentication == null) {
            model.addAttribute("favoritos", java.util.Collections.emptyList());
            return "favoritos/listar";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            model.addAttribute("favoritos", java.util.Collections.emptyList());
            return "favoritos/listar";
        }

        List<Favorito> favoritos = favoritoService.obtenerFavoritos(usuario.getId());
        model.addAttribute("favoritos", favoritos);

        return "favoritos/listar";
    }

    @PostMapping("/agregar")
    @ResponseBody
    public ResponseEntity<?> agregarFavorito(@RequestParam Long productoId, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        Favorito favorito = favoritoService.agregarFavorito(usuario.getId(), productoId);
        if (favorito != null) {
            return ResponseEntity.ok().body("Producto agregado a favoritos");
        } else {
            return ResponseEntity.badRequest().body("Error al agregar favorito");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarFavorito(@PathVariable Long id) {
        favoritoService.eliminarFavorito(id);
        return ResponseEntity.ok().body("Favorito eliminado");
    }
}

