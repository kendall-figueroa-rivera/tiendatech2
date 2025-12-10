package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiendatech2.model.Resena;
import tiendatech2.repository.ResenaRepository;
import tiendatech2.service.ResenaService;
import java.util.List;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public Resena guardar(Resena resena) {
        return resenaRepository.save(resena);
    }

    @Override
    public Resena buscarPorId(Long id) {
        return resenaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Resena> buscarPorProducto(Long productoId) {
        return resenaRepository.findByProductoIdOrderByFechaCreacionDesc(productoId);
    }

    @Override
    public List<Resena> buscarPorUsuario(Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Double calcularPromedioCalificacion(Long productoId) {
        Double promedio = resenaRepository.calcularPromedioCalificacion(productoId);
        return promedio != null ? promedio : 0.0;
    }

    @Override
    public Long contarResenas(Long productoId) {
        return resenaRepository.contarPorProducto(productoId);
    }

    @Override
    public boolean usuarioYaCalifico(Long usuarioId, Long productoId) {
        return resenaRepository.findByUsuarioId(usuarioId).stream()
            .anyMatch(r -> r.getProducto().getId().equals(productoId));
    }
}

