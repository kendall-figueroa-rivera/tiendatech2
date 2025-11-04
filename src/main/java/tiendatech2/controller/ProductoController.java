package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiendatech2.model.Producto;
import tiendatech2.model.Usuario;
import tiendatech2.service.*;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarProductos(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) Long categoria,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) BigDecimal minPrecio,
            @RequestParam(required = false) BigDecimal maxPrecio,
            Authentication authentication,
            Model model) {
        
        List<Producto> productos;
        
        if (busqueda != null && !busqueda.isEmpty()) {
            productos = productoService.buscarProductos(busqueda);
        } else if (categoria != null) {
            productos = productoService.buscarPorCategoria(categoria);
        } else if (marca != null && !marca.isEmpty()) {
            productos = productoService.buscarPorMarca(marca);
        } else if (minPrecio != null && maxPrecio != null) {
            productos = productoService.buscarPorPrecio(minPrecio, maxPrecio);
        } else {
            productos = productoService.listarActivos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.listarActivas());
        model.addAttribute("ofertas", ofertaService.listarActivas());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("categoria", categoria);
        model.addAttribute("marca", marca);
        model.addAttribute("minPrecio", minPrecio);
        model.addAttribute("maxPrecio", maxPrecio);
        
        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
            if (usuario != null) {
                model.addAttribute("usuarioId", usuario.getId());
            }
        }
        
        return "productos/listar";
    }

    @GetMapping("/{id}")
    public String verProducto(@PathVariable Long id, Authentication authentication, Model model) {
        Producto producto = productoService.buscarPorId(id);
        if (producto == null || !producto.getActivo()) {
            return "redirect:/productos";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("comentarios", comentarioService.buscarPorProducto(id));
        
        if (authentication != null) {
            Usuario usuario = usuarioService.buscarPorCorreo(authentication.getName());
            if (usuario != null) {
                model.addAttribute("usuarioId", usuario.getId());
                model.addAttribute("esFavorito", favoritoService.esFavorito(usuario.getId(), id));
            }
        } else {
            model.addAttribute("esFavorito", false);
        }
        
        return "productos/detalle";
    }
}

