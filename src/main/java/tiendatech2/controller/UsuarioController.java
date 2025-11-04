/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiendatech2.model.Usuario;
import tiendatech2.model.Rol;
import tiendatech2.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public String perfil(Authentication authentication, Model model) {
        // Para pruebas sin autenticación - crear un usuario de ejemplo
        if (authentication == null) {
            Usuario usuarioEjemplo = new Usuario();
            usuarioEjemplo.setId(0L);
            usuarioEjemplo.setNombre("Usuario de Prueba");
            usuarioEjemplo.setCorreo("prueba@ejemplo.com");
            usuarioEjemplo.setDireccion("Dirección de prueba");
            usuarioEjemplo.setTelefono("0000-0000");
            Rol rolEjemplo = new Rol();
            rolEjemplo.setNombre("USER");
            usuarioEjemplo.setRol(rolEjemplo);
            model.addAttribute("usuario", usuarioEjemplo);
            return "usuario/perfil";
        }

        Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuario == null) {
            Usuario usuarioEjemplo = new Usuario();
            usuarioEjemplo.setId(0L);
            usuarioEjemplo.setNombre("Usuario de Prueba");
            usuarioEjemplo.setCorreo("prueba@ejemplo.com");
            model.addAttribute("usuario", usuarioEjemplo);
            return "usuario/perfil";
        }

        model.addAttribute("usuario", usuario);
        return "usuario/perfil";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(
            @ModelAttribute Usuario usuario,
            @RequestParam(required = false) String contrasenaActual,
            @RequestParam(required = false) String nuevaContrasena,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        
        if (authentication == null) {
            return "redirect:/auth/login";
        }

        Usuario usuarioActual = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuarioActual == null) {
            return "redirect:/auth/login";
        }

        // Validar contraseña actual si se quiere cambiar la contraseña
        if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
            if (contrasenaActual == null || !passwordEncoder.matches(contrasenaActual, usuarioActual.getContrasena())) {
                redirectAttributes.addFlashAttribute("error", "La contraseña actual es incorrecta");
                return "redirect:/usuario/perfil";
            }
            usuario.setContrasena(nuevaContrasena);
        } else {
            usuario.setContrasena(null); // No cambiar contraseña
        }

        usuario.setId(usuarioActual.getId());
        usuario.setRol(usuarioActual.getRol());
        usuario.setEmailConfirmado(usuarioActual.getEmailConfirmado());

        Usuario usuarioActualizado = usuarioService.actualizar(usuario);
        if (usuarioActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil");
        }

        return "redirect:/usuario/perfil";
    }
}