package tiendatech2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tiendatech2.service.CuponService;

@Controller
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @PostMapping("/validar")
    @ResponseBody
    public ResponseEntity<?> validarCupon(@RequestParam String codigo, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Debe iniciar sesión");
        }

        boolean valido = cuponService.validarCupon(codigo);
        if (valido) {
            return ResponseEntity.ok().body("Cupón válido");
        } else {
            return ResponseEntity.badRequest().body("Cupón inválido o expirado");
        }
    }
}

