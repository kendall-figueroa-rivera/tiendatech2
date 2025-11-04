package tiendatech2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeDescuento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioDescuento;

    @Column(name = "fecha_inicio", nullable = false)
    private Timestamp fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Timestamp fechaFin;

    @Column(nullable = false)
    private Boolean activa = true;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public BigDecimal getPrecioDescuento() {
        return precioDescuento;
    }

    public void setPrecioDescuento(BigDecimal precioDescuento) {
        this.precioDescuento = precioDescuento;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}

