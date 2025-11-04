package tiendatech2.service;

public interface EmailService {
    void enviarEmailConfirmacion(String to, String token);
    void enviarEmailBienvenida(String to, String nombre);
}

