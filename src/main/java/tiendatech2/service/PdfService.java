package tiendatech2.service;

import tiendatech2.model.Pedido;
import java.io.ByteArrayOutputStream;

public interface PdfService {
    ByteArrayOutputStream generarFactura(Pedido pedido);
}

