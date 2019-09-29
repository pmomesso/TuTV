package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.MailService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String content, boolean containsHTML) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);

            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, containsHTML);
            messageHelper.setFrom("noreplytutv@gmail.com", "Equipo TuTv"); //TODO REPITO EL MAIL, PONERLO COMO CTE?
        } catch (Exception e) {
            //TODO como manejamos esto?
        }

        mailSender.send(message);
    }

    @Override
    public void sendConfirmationMail(User u, String token) {
        //TODO chequear que no exista el token, ponerle expiración, algo así?
        String body = "¡Gracias por registrarse a TuTv!<br>" +
                "<a href='" + token + "'>CLICK AQUI PARA CONFIRMAR CUENTA</a>";

        sendMail(u.getMailAddress(), "Confirmar mail", body, true);
    }
}
