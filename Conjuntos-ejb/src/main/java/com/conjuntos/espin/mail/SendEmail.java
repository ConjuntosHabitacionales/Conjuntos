package com.conjuntos.espin.mail;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmail {

    private static final Logger log = Logger.getLogger(SendEmail.class.getName());

    private String destinatario;
    private String subject;
    private String mensaje;
    private Session session;
    private String fileName;
    private Properties props;

    public SendEmail() {
        this.props = new Properties();
    }

    public void setAttachemnt(String attachment) {
        this.fileName = attachment;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void cargarConfiguracion() throws GeneralSecurityException {

        final String username = "developersninja@gmail.com";
        final String password = "Espe.2016";

        this.props.put("mail.smtp.host", "smtp.gmail.com");
        this.props.put("mail.smtp.socketFactory.port", "465");
        this.props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        this.props.put("mail.smtp.auth", "true");
        this.props.put("mail.smtp.port", "465");
        this.props.put("mail.smtp.ssl.enable", "true");
        this.props.put("mail.username", "developersninja@gmail.com");
        this.props.put("mail.password", "Espe.2016");
        this.props.put("mail.accoun", "developersninja@gmail.com");
        this.props.put("mail.screenname", "HOGAR");

        this.session = Session.getInstance(this.props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean enviarCorreo() {
        boolean exito = false;
        try {//this.prop.$MAIL_ACCOUNT, this.prop.$MAIL_SCREEN_NAME)
            MimeMessage message = new MimeMessage(this.session);
            message.setFrom(new InternetAddress(this.props.getProperty("mail.account"), this.props.getProperty("mail.screenname")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject(subject);

            /**
             * Insert html
             */
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart html = new MimeBodyPart();
            String htmlText = mensaje;
            html.setContent(htmlText, "text/html; charset=utf-8");
            multipart.addBodyPart(html);

            message.setContent(multipart);
            Transport.send(message);
            exito = true;
            log.log(Level.INFO, "Se envio el correo electrónico");
        } catch (MessagingException ex) {
            log.log(Level.SEVERE, "No se envio error ENVIO Messaging: " + ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            log.log(Level.SEVERE, "No se envio error ENCODING Unsupported Encoding Exception: "+ ex.getMessage());
        }
        return exito;
    }
}
