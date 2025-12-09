package tiendatech2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tiendatech2.model.Rol;
import tiendatech2.model.Usuario;
import tiendatech2.repository.RolRepository;
import tiendatech2.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.count() == 0) {
            Rol admin = new Rol();
            admin.setNombre("ADMIN");
            admin.setDescripcion("Administrador del sistema");
            rolRepository.save(admin);

            Rol vendedor = new Rol();
            vendedor.setNombre("VENDEDOR");
            vendedor.setDescripcion("Vendedor de productos");
            rolRepository.save(vendedor);

            Rol cliente = new Rol();
            cliente.setNombre("CLIENTE");
            cliente.setDescripcion("Cliente de la tienda");
            rolRepository.save(cliente);

            System.out.println("✓ Roles creados");
        }

        if (usuarioRepository.count() == 0) {
            Rol adminRole = rolRepository.findByNombre("ADMIN").orElseThrow();
            Rol vendedorRole = rolRepository.findByNombre("VENDEDOR").orElseThrow();
            Rol clienteRole = rolRepository.findByNombre("CLIENTE").orElseThrow();

            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setCorreo("admin@tiendatech.com");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol(adminRole);
            admin.setEmailConfirmado(true);
            usuarioRepository.save(admin);

            Usuario vendedor = new Usuario();
            vendedor.setNombre("Vendedor");
            vendedor.setCorreo("vendedor@tiendatech.com");
            vendedor.setContrasena(passwordEncoder.encode("vendedor123"));
            vendedor.setRol(vendedorRole);
            vendedor.setEmailConfirmado(true);
            usuarioRepository.save(vendedor);

            Usuario cliente = new Usuario();
            cliente.setNombre("Cliente Demo");
            cliente.setCorreo("cliente@gmail.com");
            cliente.setContrasena(passwordEncoder.encode("cliente123"));
            cliente.setRol(clienteRole);
            cliente.setEmailConfirmado(true);
            usuarioRepository.save(cliente);

            System.out.println("✓ Usuarios de prueba creados:");
            System.out.println("  Admin:    admin@tiendatech.com / admin123");
            System.out.println("  Vendedor: vendedor@tiendatech.com / vendedor123");
            System.out.println("  Cliente:  cliente@gmail.com / cliente123");
        }
    }
}
