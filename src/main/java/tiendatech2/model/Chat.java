package tiendatech2.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_inicio", nullable = false, updatable = false)
    private Timestamp fechaInicio;

    @Column(name = "fecha_cierre")
    private Timestamp fechaCierre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoChat estado;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Mensaje> mensajes;

    public enum EstadoChat {
        ABIERTO,
        CERRADO
    }

    @PrePersist
    protected void onCreate() {
        fechaInicio = new Timestamp(System.currentTimeMillis());
        if (estado == null) {
            estado = EstadoChat.ABIERTO;
        }
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

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Timestamp fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public EstadoChat getEstado() {
        return estado;
    }

    public void setEstado(EstadoChat estado) {
        this.estado = estado;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}

