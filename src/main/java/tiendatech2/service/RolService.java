/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package tiendatech2.service;
import tiendatech2.model.Rol;

public interface RolService {
    Rol guardar(Rol rol);
    Rol buscarPorNombre(String nombre);
}