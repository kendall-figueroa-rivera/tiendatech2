package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiendatech2.model.*;
import tiendatech2.service.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public String panelAdmin(Model model) {
        model.addAttribute("totalUsuarios", usuarioService.listarTodos().size());
        model.addAttribute("totalProductos", productoService.listarTodos().size());
        model.addAttribute("totalPedidos", pedidoService.listarTodos().size());
        model.addAttribute("productosStockBajo", productoService.findProductosConStockBajo());
        return "admin/panel";
    }

    @GetMapping("/usuarios")
    public String gestionUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("roles", List.of("ADMIN", "VENDEDOR", "USER"));
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/rol")
    public String asignarRol(@PathVariable Long id, @RequestParam String rolNombre, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            Rol rol = rolService.buscarPorNombre(rolNombre);
            if (rol == null) {
                rol = new Rol();
                rol.setNombre(rolNombre);
                rol.setDescripcion("Rol " + rolNombre);
                rol = rolService.guardar(rol);
            }
            usuario.setRol(rol);
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensaje", "Rol asignado exitosamente");
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        // Verificar que no se está eliminando a sí mismo
        Usuario usuarioActual = usuarioService.buscarPorCorreo(authentication.getName());
        if (usuarioActual != null && usuarioActual.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("error", "No puedes eliminar tu propia cuenta");
            return "redirect:/admin/usuarios";
        }

        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            try {
                usuarioService.eliminar(id);
                redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado exitosamente");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el usuario. Puede tener datos relacionados.");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/productos")
    public String gestionProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "admin/productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categoriaService.listarActivas());
        return "admin/producto-form";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute Producto producto, @RequestParam Long categoriaId) {
        Categoria categoria = categoriaService.buscarPorId(categoriaId);
        producto.setCategoria(categoria);
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @PostMapping("/productos/{id}/stock")
    public String actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        productoService.actualizarStock(id, stock);
        return "redirect:/admin/productos";
    }

    @GetMapping("/categorias")
    public String gestionCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("categoria", new tiendatech2.model.Categoria());
        return "admin/categorias";
    }

    @PostMapping("/categorias")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias";
    }

    @PostMapping("/categorias/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return "redirect:/admin/categorias";
    }

    @GetMapping("/pedidos")
    public String gestionPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "admin/pedidos";
    }

    @PostMapping("/pedidos/{id}/estado")
    public String actualizarEstadoPedido(@PathVariable Long id, @RequestParam String estado) {
        Pedido.EstadoPedido estadoPedido = Pedido.EstadoPedido.valueOf(estado);
        pedidoService.actualizarEstado(id, estadoPedido);
        return "redirect:/admin/pedidos";
    }

    @GetMapping("/ofertas")
    public String gestionOfertas(Model model) {
        model.addAttribute("ofertas", ofertaService.listarActivas());
        model.addAttribute("productos", productoService.listarActivos());
        model.addAttribute("categorias", categoriaService.listarActivas());
        return "admin/ofertas";
    }

    @PostMapping("/ofertas")
    public String guardarOferta(
            @RequestParam Long productoId,
            @RequestParam BigDecimal porcentajeDescuento,
            @RequestParam Timestamp fechaInicio,
            @RequestParam Timestamp fechaFin) {
        
        Producto producto = productoService.buscarPorId(productoId);
        if (producto != null) {
            Oferta oferta = new Oferta();
            oferta.setProducto(producto);
            oferta.setPorcentajeDescuento(porcentajeDescuento);
            BigDecimal precioDescuento = producto.getPrecio().subtract(
                producto.getPrecio().multiply(porcentajeDescuento).divide(new BigDecimal("100"))
            );
            oferta.setPrecioDescuento(precioDescuento);
            oferta.setFechaInicio(fechaInicio);
            oferta.setFechaFin(fechaFin);
            oferta.setActiva(true);
            ofertaService.guardar(oferta);
        }
        return "redirect:/admin/ofertas";
    }

    @PostMapping("/ofertas/{id}/eliminar")
    public String eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminar(id);
        return "redirect:/admin/ofertas";
    }
}

