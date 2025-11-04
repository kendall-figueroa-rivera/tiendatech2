package tiendatech2.service;

import tiendatech2.model.MetodoPago;
import java.util.List;

public interface MetodoPagoService {
    MetodoPago guardar(MetodoPago metodoPago);
    MetodoPago buscarPorId(Long id);
    List<MetodoPago> listarActivos();
}

