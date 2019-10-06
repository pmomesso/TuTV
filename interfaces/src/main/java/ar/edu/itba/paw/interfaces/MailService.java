package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendMail(String to, String subject, String content, boolean containsHTML);

    void sendConfirmationMail(User u, String token, String BaseUrl);
}
