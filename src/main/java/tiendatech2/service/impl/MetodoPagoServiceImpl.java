package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendatech2.model.MetodoPago;
import tiendatech2.repository.MetodoPagoRepository;
import tiendatech2.service.MetodoPagoService;
import java.util.List;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Override
    public MetodoPago guardar(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    @Override
    public MetodoPago buscarPorId(Long id) {
        return metodoPagoRepository.findById(id).orElse(null);
    }

    @Override
    public List<MetodoPago> listarActivos() {
        return metodoPagoRepository.findByActivoTrue();
    }
}

