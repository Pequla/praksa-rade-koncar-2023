package rs.ac.singidunum;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {
    public static final String FROM = "pkresoja@singidunum.ac.rs";
    public static final String PASSWORD = "rltbeczllfwzyaso";
    public static final String SMPT_HOST = "smtp.gmail.com";

    private static MailService instance;
    private Session session;

    private MailService() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMPT_HOST);
        props.put("mail.smtp.port", "587");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        };

        this.session = Session.getInstance(props, authenticator);
    }

    public static MailService getInstance() {
        if (instance == null) {
            instance = new MailService();
        }
        return instance;
    }

    public void sendEmil(String to, String title, String body) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(title);
        message.setText(body);
        Transport.send(message);
        System.out.println("Mail je poslat na Vasu adresu");
    }
}
