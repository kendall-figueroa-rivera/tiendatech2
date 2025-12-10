package tiendatech2.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "productos_vistos")
public class ProductoVisto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // null si es usuario no autenticado

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "session_id")
    private String sessionId; // Para usuarios no autenticados

    @Column(name = "fecha_visto", nullable = false, updatable = false)
    private Timestamp fechaVisto;

    @PrePersist
    protected void onCreate() {
        fechaVisto = new Timestamp(System.currentTimeMillis());
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getFechaVisto() {
        return fechaVisto;
    }

    public void setFechaVisto(Timestamp fechaVisto) {
        this.fechaVisto = fechaVisto;
    }
}

