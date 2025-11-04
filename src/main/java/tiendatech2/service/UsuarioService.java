/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.service;

import tiendatech2.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorCorreo(String correo);
    Usuario buscarPorUsername(String username);
    Usuario buscarPorId(Long id);
    Usuario actualizar(Usuario usuario);
    List<Usuario> listarTodos();
    Usuario confirmarEmail(String token);
    Usuario buscarPorTokenConfirmacion(String token);
}