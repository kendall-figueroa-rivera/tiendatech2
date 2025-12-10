package tiendatech2.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
@SessionAttributes("carrito")
public class CarritoController {

        BigDecimal subtotal = items.stream()
    // mapa en sesión: productId -> cantidad
    @ModelAttribute("carrito")
    public Map<Long, Integer> carrito() {
        return new LinkedHashMap<>();
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam("productoId") Long productoId,
                          @RequestParam(value = "cantidad", defaultValue = "1") int cantidad,
                          @ModelAttribute("carrito") Map<Long, Integer> carrito,
                          RedirectAttributes ra) {
        if (productoId == null || cantidad <= 0) {
            ra.addFlashAttribute("toastError", "Producto o cantidad inválida");
            return "redirect:/productos";
        }
        carrito.merge(productoId, cantidad, Integer::sum);
        ra.addFlashAttribute("toastOk", "Producto agregado al carrito");
        return "redirect:/productos";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("productoId") Long productoId,
                           @ModelAttribute("carrito") Map<Long, Integer> carrito,
                           RedirectAttributes ra) {
        carrito.remove(productoId);
        ra.addFlashAttribute("toastOk", "Producto eliminado del carrito");
        return "redirect:/carrito";
    }

    @GetMapping
    public String ver(@ModelAttribute("carrito") Map<Long, Integer> carrito, Model model) {
        model.addAttribute("items", carrito); // ya luego lo cruzas con productos si quieres
        return "carrito/index"; // templates.carrito/index.html
    }
}
