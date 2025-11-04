/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Usuario;
import tiendatech2.model.Rol;
import tiendatech2.service.UsuarioService;
import tiendatech2.service.RolService;
import tiendatech2.service.EmailService;
import jakarta.validation.Valid;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{6,}$");

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales inválidas");
        }
        return "auth/login";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, BindingResult result, Model model) {
        // Validación de correo
        if (usuario.getCorreo() == null || usuario.getCorreo().isEmpty()) {
            model.addAttribute("error", "El correo electrónico es requerido");
            return "auth/registro";
        }
        if (!EMAIL_PATTERN.matcher(usuario.getCorreo()).matches()) {
            model.addAttribute("error", "El correo electrónico no es válido");
            return "auth/registro";
        }
        if (usuarioService.buscarPorCorreo(usuario.getCorreo()) != null) {
            model.addAttribute("error", "Este correo electrónico ya está registrado");
            return "auth/registro";
        }

        // Validación de contraseña
        if (usuario.getContrasena() == null || usuario.getContrasena().length() < 6) {
            model.addAttribute("error", "La contraseña debe tener al menos 6 caracteres");
            return "auth/registro";
        }
        if (!PASSWORD_PATTERN.matcher(usuario.getContrasena()).matches()) {
            model.addAttribute("error", "La contraseña debe contener al menos una letra y un número");
            return "auth/registro";
        }

        // Buscar o crear el rol USER
        Rol rolUser = rolService.buscarPorNombre("USER");
        if (rolUser == null) {
            rolUser = new Rol();
            rolUser.setNombre("USER");
            rolUser.setDescripcion("Rol básico de usuario");
            rolService.guardar(rolUser);
        }

        // Asignar el rol y guardar
        usuario.setRol(rolUser);
        usuario.setEmailConfirmado(false);
        Usuario usuarioGuardado = usuarioService.guardar(usuario);

        // Enviar correo de confirmación
        emailService.enviarEmailConfirmacion(usuario.getCorreo(), usuarioGuardado.getTokenConfirmacion());
        emailService.enviarEmailBienvenida(usuario.getCorreo(), usuario.getNombre());

        return "redirect:/auth/login?registrado=true";
    }

    @GetMapping("/confirmar")
    public String confirmarEmail(@RequestParam String token, Model model) {
        Usuario usuario = usuarioService.confirmarEmail(token);
        if (usuario != null) {
            model.addAttribute("mensaje", "Email confirmado exitosamente");
            return "redirect:/auth/login?confirmado=true";
        } else {
            model.addAttribute("error", "Token de confirmación inválido");
            return "auth/login";
        }
    }
}