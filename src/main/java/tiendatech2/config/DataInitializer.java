package tiendatech2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tiendatech2.model.*;
import tiendatech2.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo inicializar si no hay datos
        if (rolRepository.count() == 0) {
            initRoles();
            initUsuarios();
            initCategorias();
            initMetodosPago();
            initProductos();
            System.out.println("✅ Datos iniciales cargados exitosamente!");
        } else {
            System.out.println("ℹ️  Los datos ya existen en la base de datos.");
        }
    }

    private void initRoles() {
        Rol admin = new Rol();
        admin.setNombre("ROLE_ADMIN");
        admin.setDescripcion("Administrador del sistema");
        rolRepository.save(admin);

        Rol user = new Rol();
        user.setNombre("ROLE_USER");
        user.setDescripcion("Usuario regular");
        rolRepository.save(user);

        System.out.println("✅ Roles creados");
    }

    private void initUsuarios() {
        Rol adminRole = rolRepository.findByNombre("ROLE_ADMIN").orElse(null);
        Rol userRole = rolRepository.findByNombre("ROLE_USER").orElse(null);

        // Usuario Administrador
        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setCorreo("admin@tiendatech.com");
        admin.setContrasena(passwordEncoder.encode("admin123"));
        admin.setRol(adminRole);
        admin.setDireccion("San José, Costa Rica");
        admin.setTelefono("2222-3333");
        admin.setEmailConfirmado(true);
        usuarioRepository.save(admin);

        // Usuario Regular 1
        Usuario user1 = new Usuario();
        user1.setNombre("Juan Pérez");
        user1.setCorreo("juan@example.com");
        user1.setContrasena(passwordEncoder.encode("user123"));
        user1.setRol(userRole);
        user1.setDireccion("Heredia, Costa Rica");
        user1.setTelefono("8888-9999");
        user1.setEmailConfirmado(true);
        usuarioRepository.save(user1);

        // Usuario Regular 2
        Usuario user2 = new Usuario();
        user2.setNombre("María González");
        user2.setCorreo("maria@example.com");
        user2.setContrasena(passwordEncoder.encode("user123"));
        user2.setRol(userRole);
        user2.setDireccion("Alajuela, Costa Rica");
        user2.setTelefono("7777-6666");
        user2.setEmailConfirmado(true);
        usuarioRepository.save(user2);

        System.out.println("✅ Usuarios creados");
        System.out.println("   Admin: admin@tiendatech.com / admin123");
        System.out.println("   User1: juan@example.com / user123");
        System.out.println("   User2: maria@example.com / user123");
    }

    private void initCategorias() {
        String[] categorias = {
            "Laptops", "Smartphones", "Tablets", "Accesorios",
            "Audio", "Gaming", "Cámaras", "Televisores"
        };

        for (String cat : categorias) {
            Categoria categoria = new Categoria();
            categoria.setNombre(cat);
            categoria.setDescripcion("Productos de " + cat);
            categoria.setActiva(true);
            categoriaRepository.save(categoria);
        }

        System.out.println("✅ Categorías creadas");
    }

    private void initMetodosPago() {
        String[][] metodos = {
            {"Tarjeta de Crédito", "Pago con tarjeta de crédito"},
            {"Tarjeta de Débito", "Pago con tarjeta de débito"},
            {"Transferencia Bancaria", "Transferencia bancaria directa"},
            {"Sinpe Móvil", "Pago con Sinpe Móvil"},
            {"Efectivo", "Pago en efectivo contra entrega"}
        };

        for (String[] metodo : metodos) {
            MetodoPago mp = new MetodoPago();
            mp.setNombre(metodo[0]);
            mp.setDescripcion(metodo[1]);
            mp.setActivo(true);
            metodoPagoRepository.save(mp);
        }

        System.out.println("✅ Métodos de pago creados");
    }

    private void initProductos() {
        Categoria laptops = categoriaRepository.findByNombre("Laptops").orElse(null);
        Categoria smartphones = categoriaRepository.findByNombre("Smartphones").orElse(null);
        Categoria tablets = categoriaRepository.findByNombre("Tablets").orElse(null);
        Categoria accesorios = categoriaRepository.findByNombre("Accesorios").orElse(null);
        Categoria audio = categoriaRepository.findByNombre("Audio").orElse(null);

        // Laptops
        crearProducto("MacBook Pro 16", "Laptop profesional Apple con chip M3 Pro",
            new BigDecimal("2499.99"), "Apple", 15, laptops,
            "https://images.unsplash.com/photo-1517336714731-489689fd1ca8");

        crearProducto("Dell XPS 15", "Laptop premium con pantalla InfinityEdge",
            new BigDecimal("1799.99"), "Dell", 20, laptops,
            "https://images.unsplash.com/photo-1593642632823-8f785ba67e45");

        crearProducto("ThinkPad X1 Carbon", "Laptop ultraligera para profesionales",
            new BigDecimal("1599.99"), "Lenovo", 12, laptops,
            "https://images.unsplash.com/photo-1588872657578-7efd1f1555ed");

        // Smartphones
        crearProducto("iPhone 15 Pro", "Smartphone Apple con titanio y chip A17 Pro",
            new BigDecimal("1199.99"), "Apple", 25, smartphones,
            "https://images.unsplash.com/photo-1592286927405-ed41e141b729");

        crearProducto("Samsung Galaxy S24 Ultra", "Smartphone premium con S Pen",
            new BigDecimal("1099.99"), "Samsung", 30, smartphones,
            "https://images.unsplash.com/photo-1610945415295-d9bbf067e59c");

        crearProducto("Google Pixel 8 Pro", "Smartphone con mejor cámara IA",
            new BigDecimal("899.99"), "Google", 18, smartphones,
            "https://images.unsplash.com/photo-1598327105666-5b89351aff97");

        // Tablets
        crearProducto("iPad Pro 12.9", "Tablet profesional con chip M2",
            new BigDecimal("1099.99"), "Apple", 10, tablets,
            "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0");

        crearProducto("Samsung Galaxy Tab S9", "Tablet Android premium",
            new BigDecimal("799.99"), "Samsung", 15, tablets,
            "https://images.unsplash.com/photo-1585790050230-5dd28404f490");

        // Accesorios
        crearProducto("Magic Mouse", "Mouse inalámbrico recargable",
            new BigDecimal("79.99"), "Apple", 50, accesorios,
            "https://images.unsplash.com/photo-1527814050087-3793815479db");

        crearProducto("MX Master 3S", "Mouse ergonómico profesional",
            new BigDecimal("99.99"), "Logitech", 40, accesorios,
            "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7");

        crearProducto("Teclado Mecánico K380", "Teclado Bluetooth multi-dispositivo",
            new BigDecimal("49.99"), "Logitech", 35, accesorios,
            "https://images.unsplash.com/photo-1587829741301-dc798b83add3");

        // Audio
        crearProducto("AirPods Pro 2", "Audífonos con cancelación de ruido",
            new BigDecimal("249.99"), "Apple", 45, audio,
            "https://images.unsplash.com/photo-1606841837239-c5a1a4a07af7");

        crearProducto("Sony WH-1000XM5", "Audífonos over-ear premium",
            new BigDecimal("399.99"), "Sony", 20, audio,
            "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb");

        crearProducto("JBL Charge 5", "Bocina Bluetooth portátil",
            new BigDecimal("179.99"), "JBL", 30, audio,
            "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1");

        System.out.println("✅ Productos creados (14 productos)");
    }

    private void crearProducto(String nombre, String descripcion, BigDecimal precio,
                              String marca, int stock, Categoria categoria, String imagenUrl) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setMarca(marca);
        producto.setStock(stock);
        producto.setStockMinimo(5);
        producto.setCategoria(categoria);
        producto.setImagenUrl(imagenUrl);
        producto.setActivo(true);
        productoRepository.save(producto);
    }
}
