package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tiendatech2.model.Oferta;
import tiendatech2.repository.OfertaRepository;
import tiendatech2.service.OfertaService;
import java.sql.Timestamp;
import java.util.List;

@Service
public class OfertaServiceImpl implements OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    public Oferta guardar(Oferta oferta) {
        return ofertaRepository.save(oferta);
    }

    @Override
    public Oferta buscarPorId(Long id) {
        return ofertaRepository.findById(id).orElse(null);
    }

    @Override
    public Oferta buscarPorProducto(Long productoId) {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        return ofertaRepository.findOfertaActivaPorProducto(productoId, ahora).orElse(null);
    }

    @Override
    public List<Oferta> listarActivas() {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        return ofertaRepository.findOfertasActivas(ahora);
    }

    @Override
    public void eliminar(Long id) {
        Oferta oferta = ofertaRepository.findById(id).orElse(null);
        if (oferta != null) {
            oferta.setActiva(false);
            ofertaRepository.save(oferta);
        }
    }

    @Override
    @Scheduled(cron = "0 0 * * * *") // Cada hora
    public void actualizarOfertasActivas() {
        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        List<Oferta> todasOfertas = ofertaRepository.findByActivaTrue();
        for (Oferta oferta : todasOfertas) {
            if (ahora.after(oferta.getFechaFin()) || ahora.before(oferta.getFechaInicio())) {
                oferta.setActiva(false);
                ofertaRepository.save(oferta);
            }
        }
    }
}

