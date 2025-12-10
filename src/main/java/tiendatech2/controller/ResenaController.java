package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Resena;
import tiendatech2.model.Usuario;
import tiendatech2.service.ResenaService;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.ProductoService;
import java.util.List;

@Controller
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearResena(@RequestParam Long productoId,
                                        @RequestParam Integer calificacion,
                                        @RequestParam(required = false) String comentario,
                                        Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        if (calificacion < 1 || calificacion > 5) {
            return ResponseEntity.badRequest().body("La calificación debe estar entre 1 y 5");
        }

        if (resenaService.usuarioYaCalifico(usuario.getId(), productoId)) {
            return ResponseEntity.badRequest().body("Ya has calificado este producto");
        }

        Resena resena = new Resena();
        resena.setUsuario(usuario);
        resena.setProducto(productoService.buscarPorId(productoId));
        resena.setCalificacion(calificacion);
        resena.setComentario(comentario);

        resenaService.guardar(resena);
        return ResponseEntity.ok().body("Reseña creada exitosamente");
    }

    @GetMapping("/producto/{productoId}")
    @ResponseBody
    public ResponseEntity<?> obtenerResenas(@PathVariable Long productoId) {
        List<Resena> resenas = resenaService.buscarPorProducto(productoId);
        Double promedio = resenaService.calcularPromedioCalificacion(productoId);
        Long total = resenaService.contarResenas(productoId);

        java.util.Map<String, Object> respuesta = new java.util.HashMap<>();
        respuesta.put("resenas", resenas);
        respuesta.put("promedio", promedio);
        respuesta.put("total", total);

        return ResponseEntity.ok(respuesta);
    }
}

