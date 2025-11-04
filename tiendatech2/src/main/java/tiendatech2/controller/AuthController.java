/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Usuario;
import tiendatech2.model.Rol;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.RolService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder; // 🔐 Para cifrar contraseñas

    // ===== Página de inicio de sesión =====
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // ===== Página de registro =====
    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    // ===== Registrar nuevo usuario =====
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        // Buscar o crear el rol USER
        Rol rolUser = rolService.buscarPorNombre("USER");
        if (rolUser == null) {
            rolUser = new Rol();
            rolUser.setNombre("USER");
            rolUser.setDescripcion("Rol básico de usuario");
            rolService.guardar(rolUser);
        }

        // 🔐 Encriptar contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Asignar el rol y guardar
        usuario.setRol(rolUser);
        usuarioService.guardar(usuario);

        return "redirect:/auth/login?registrado";
    }
}