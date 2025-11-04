package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Comentario;
import tiendatech2.model.Usuario;
import tiendatech2.service.ComentarioService;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.ProductoService;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> agregarComentario(
            @RequestParam Long productoId,
            @RequestParam String contenido,
            Authentication authentication) {
        
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        if (productoService.buscarPorId(productoId) == null) {
            return ResponseEntity.badRequest().body("Producto no encontrado");
        }

        Comentario comentario = new Comentario();
        comentario.setUsuario(usuario);
        comentario.setProducto(productoService.buscarPorId(productoId));
        comentario.setContenido(contenido);

        Comentario comentarioGuardado = comentarioService.guardar(comentario);
        return ResponseEntity.ok(comentarioGuardado);
    }
}

