package tiendatech2.service;

import tiendatech2.model.Oferta;
import java.sql.Timestamp;
import java.util.List;

public interface OfertaService {
    Oferta guardar(Oferta oferta);
    Oferta buscarPorId(Long id);
    Oferta buscarPorProducto(Long productoId);
    List<Oferta> listarActivas();
    void eliminar(Long id);
    void actualizarOfertasActivas();
}

