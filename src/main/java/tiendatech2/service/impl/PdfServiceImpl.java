package tiendatech2.service.impl;

import org.springframework.stereotype.Service;
import tiendatech2.model.Pedido;
import tiendatech2.service.PdfService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public ByteArrayOutputStream generarFactura(Pedido pedido) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            // Crear documento HTML simple (fácil de convertir a PDF)
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>");
            html.append("<style>body{font-family:Arial;margin:20px;}table{width:100%;border-collapse:collapse;}th,td{padding:8px;text-align:left;border-bottom:1px solid #ddd;}</style>");
            html.append("</head><body>");
            html.append("<h1>FACTURA #").append(pedido.getId()).append("</h1>");
            html.append("<p><strong>Fecha:</strong> ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(pedido.getFechaPedido())).append("</p>");
            html.append("<p><strong>Cliente:</strong> ").append(pedido.getUsuario().getNombre()).append("</p>");
            html.append("<p><strong>Email:</strong> ").append(pedido.getUsuario().getCorreo()).append("</p>");
            html.append("<p><strong>Dirección:</strong> ").append(pedido.getDireccionEntrega()).append("</p>");
            html.append("<h2>Productos</h2>");
            html.append("<table><tr><th>Producto</th><th>Cantidad</th><th>Precio Unitario</th><th>Subtotal</th></tr>");
            
            pedido.getItems().forEach(item -> {
                html.append("<tr>");
                html.append("<td>").append(item.getProducto().getNombre()).append("</td>");
                html.append("<td>").append(item.getCantidad()).append("</td>");
                html.append("<td>$").append(item.getPrecioUnitario()).append("</td>");
                html.append("<td>$").append(item.getSubtotal()).append("</td>");
                html.append("</tr>");
            });
            
            html.append("</table>");
            html.append("<h3>Resumen</h3>");
            html.append("<p>Subtotal: $").append(pedido.getSubtotal()).append("</p>");
            html.append("<p>Impuestos (13%): $").append(pedido.getImpuestos()).append("</p>");
            html.append("<p><strong>Total: $").append(pedido.getTotal()).append("</strong></p>");
            html.append("</body></html>");
            
            baos.write(html.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return baos;
    }
}

