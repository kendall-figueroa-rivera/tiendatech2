package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tiendatech2.model.Cupon;
import tiendatech2.repository.CuponRepository;
import tiendatech2.service.CuponService;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CuponServiceImpl implements CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Override
    public Cupon guardar(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    @Override
    public Cupon buscarPorId(Long id) {
        return cuponRepository.findById(id).orElse(null);
    }

    @Override
    public Cupon buscarPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo).orElse(null);
    }

    @Override
    public List<Cupon> listarActivos() {
        return cuponRepository.findByActivoTrue();
    }

    @Override
    @Transactional
    public BigDecimal aplicarDescuento(String codigo, BigDecimal subtotal, Long categoriaId) {
        Cupon cupon = buscarPorCodigo(codigo);
        if (cupon == null || !validarCupon(codigo)) {
            return BigDecimal.ZERO;
        }

        // Verificar si aplica a la categoría
        if (cupon.getCategoria() != null && !cupon.getCategoria().getId().equals(categoriaId)) {
            return BigDecimal.ZERO;
        }

        BigDecimal descuento = BigDecimal.ZERO;
        if (cupon.getEsPorcentaje()) {
            descuento = subtotal.multiply(cupon.getDescuento()).divide(new BigDecimal("100"));
            if (cupon.getDescuentoMaximo() != null && descuento.compareTo(cupon.getDescuentoMaximo()) > 0) {
                descuento = cupon.getDescuentoMaximo();
            }
        } else {
            descuento = cupon.getDescuento();
            if (descuento.compareTo(subtotal) > 0) {
                descuento = subtotal;
            }
        }

        // Incrementar usos
        cupon.setUsosActuales(cupon.getUsosActuales() + 1);
        cuponRepository.save(cupon);

        return descuento;
    }

    @Override
    public boolean validarCupon(String codigo) {
        Cupon cupon = buscarPorCodigo(codigo);
        if (cupon == null) return false;
        if (!cupon.getActivo()) return false;
        
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        if (ahora.before(cupon.getFechaInicio()) || ahora.after(cupon.getFechaFin())) {
            return false;
        }
        
        if (cupon.getUsosActuales() >= cupon.getUsosMaximos()) {
            return false;
        }
        
        return true;
    }
}

