/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.service;

import tiendatech2.model.Usuario;

public interface UsuarioService {
    Usuario guardar(Usuario usuario);
    Usuario buscarPorCorreo(String correo);
    Usuario buscarPorUsername(String username); // ✅ obligatorio por el error
}