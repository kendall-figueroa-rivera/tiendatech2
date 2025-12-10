package tiendatech2.service;

import tiendatech2.model.Cupon;
import java.math.BigDecimal;
import java.util.List;

public interface CuponService {
    Cupon guardar(Cupon cupon);
    Cupon buscarPorId(Long id);
    Cupon buscarPorCodigo(String codigo);
    List<Cupon> listarActivos();
    BigDecimal aplicarDescuento(String codigo, BigDecimal subtotal, Long categoriaId);
    boolean validarCupon(String codigo);
}

