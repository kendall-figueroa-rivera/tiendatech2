package tiendatech2.model;
 
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
 
@Entity
@Table(name = "productos")
public class Producto {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String nombre;
 
    @Column(columnDefinition = "TEXT")
    private String descripcion;
 
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
 
    @Column(nullable = false)
    private String marca;
 
    @Column(nullable = false)
    private Integer stock;
 
    @Column(nullable = false)
    private Integer stockMinimo = 10;
 
    private String imagenUrl;
 
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
 
    @Column(nullable = false)
    private Boolean activo = true;
 
    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
 
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
 
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
 
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
 
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
 
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
 
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
 
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}