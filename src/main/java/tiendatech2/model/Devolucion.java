package tiendatech2.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "devoluciones")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDevolucion estado;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private Timestamp fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private Timestamp fechaResolucion;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public enum EstadoDevolucion {
        SOLICITADA,
        EN_REVISION,
        APROBADA,
        RECHAZADA,
        COMPLETADA
    }

    @PrePersist
    protected void onCreate() {
        fechaSolicitud = new Timestamp(System.currentTimeMillis());
        if (estado == null) {
            estado = EstadoDevolucion.SOLICITADA;
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoDevolucion getEstado() {
        return estado;
    }

    public void setEstado(EstadoDevolucion estado) {
        this.estado = estado;
    }

    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Timestamp getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Timestamp fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

