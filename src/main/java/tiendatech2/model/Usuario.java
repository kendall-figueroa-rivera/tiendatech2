package tiendatech2.model;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private java.sql.Timestamp fechaRegistro;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email_confirmado")
    private Boolean emailConfirmado = false;

    @Column(name = "token_confirmacion")
    private String tokenConfirmacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private java.util.List<tiendatech2.model.Pedido> pedidos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private java.util.List<tiendatech2.model.Carrito> carrito;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private java.util.List<tiendatech2.model.Favorito> favoritos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private java.util.List<tiendatech2.model.Comentario> comentarios;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private java.util.List<tiendatech2.model.Chat> chats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public java.sql.Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(java.sql.Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEmailConfirmado() {
        return emailConfirmado;
    }

    public void setEmailConfirmado(Boolean emailConfirmado) {
        this.emailConfirmado = emailConfirmado;
    }

    public String getTokenConfirmacion() {
        return tokenConfirmacion;
    }

    public void setTokenConfirmacion(String tokenConfirmacion) {
        this.tokenConfirmacion = tokenConfirmacion;
    }
}