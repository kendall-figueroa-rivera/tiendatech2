/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendatech2.model.Usuario;
import tiendatech2.repository.UsuarioRepository;
import tiendatech2.service.UsuarioService;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null && usuario.getContrasena() != null && !usuario.getContrasena().startsWith("$2a$")) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        if (usuario.getTokenConfirmacion() == null) {
            usuario.setTokenConfirmacion(UUID.randomUUID().toString());
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByNombre(username);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Usuario actualizar(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (usuarioExistente != null) {
            if (usuario.getNombre() != null) usuarioExistente.setNombre(usuario.getNombre());
            if (usuario.getCorreo() != null) usuarioExistente.setCorreo(usuario.getCorreo());
            if (usuario.getDireccion() != null) usuarioExistente.setDireccion(usuario.getDireccion());
            if (usuario.getTelefono() != null) usuarioExistente.setTelefono(usuario.getTelefono());
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty() && !usuario.getContrasena().startsWith("$2a$")) {
                usuarioExistente.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario confirmarEmail(String token) {
        Usuario usuario = usuarioRepository.findByTokenConfirmacion(token);
        if (usuario != null) {
            usuario.setEmailConfirmado(true);
            usuario.setTokenConfirmacion(null);
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public Usuario buscarPorTokenConfirmacion(String token) {
        return usuarioRepository.findByTokenConfirmacion(token);
    }
}