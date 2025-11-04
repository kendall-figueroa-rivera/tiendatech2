package tiendatech2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tiendatech2.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Override
    public void enviarEmailConfirmacion(String to, String token) {
        if (mailSender == null) {
            System.out.println("EmailService no configurado. Simulando envío de email a: " + to);
            System.out.println("Token de confirmación: " + token);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirma tu cuenta - TiendaTech2");
        message.setText("Hola,\n\nPara confirmar tu cuenta, haz clic en el siguiente enlace:\n\n" +
                "http://localhost:8080/auth/confirmar?token=" + token + "\n\n" +
                "Si no solicitaste este registro, ignora este mensaje.");
        mailSender.send(message);
    }

    @Override
    public void enviarEmailBienvenida(String to, String nombre) {
        if (mailSender == null) {
            System.out.println("EmailService no configurado. Simulando email de bienvenida a: " + to);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("¡Bienvenido a TiendaTech2!");
        message.setText("Hola " + nombre + ",\n\n" +
                "¡Gracias por registrarte en TiendaTech2!\n\n" +
                "Ya puedes comenzar a comprar nuestros productos tecnológicos.\n\n" +
                "¡Que disfrutes tu experiencia de compra!");
        mailSender.send(message);
    }
}

