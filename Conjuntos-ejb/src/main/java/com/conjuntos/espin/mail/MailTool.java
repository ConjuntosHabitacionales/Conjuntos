package com.conjuntos.espin.mail;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class MailTool implements Serializable{
    
    private static final Logger log = Logger.getLogger(MailTool.class.getName());
    
    private static String name;
    private static String link;
    private static String userName;

    public void init(String _name, String _link) {
        name = _name;
        link = _link;
    }

    public void init(String _name, String _link, String _userName) {
        name = _name;
        link = _link;
        userName = _userName;
    }

    public Boolean sendEmail(String receiver, String fileName, String estIdBanner, String estNombre, String estNota,String estFecha) {
        Boolean success = false;
        try {
            SendEmail sendEmail = new SendEmail();
            sendEmail.setDestinatario(receiver);
            sendEmail.setSubject("Notificación de Confirmación Nota Examen de Recuperación");
            sendEmail.setSubject("Correo de Confirmación");
            init("UTIC - ESPE", "ningun link");
            sendEmail.setMensaje(getMessageComprobante(fileName, estIdBanner, estNombre, estNota, estFecha));
            sendEmail.cargarConfiguracion();
            success = sendEmail.enviarCorreo();
        } catch (Exception ex) {
            log.log(Level.SEVERE,"No se envio el correo electrónico.", ex);
            success = false;
        }

        return success;
    }

    private String getMessageComprobante(String fileName, String estIdBanner, String estNombre, String estNota,String estFecha) throws Exception {
        String message = "";
        try {
            byte[] encoded = Files.readAllBytes(new File(MailTool.class.getResource(
                    "/com/conjuntos/espin/mail/resources/mail_cmp.html")
                    .toURI()).toPath());
            Charset encoding = Charset.forName("UTF-8");
            message = encoding.decode(ByteBuffer.wrap(encoded)).toString();
            message = message.replaceAll("XfileNameX", fileName);
            message = message.replaceAll("XestIdBannerX", estIdBanner);
            message = message.replaceAll("XestNombreX", estNombre);
            message = message.replaceAll("XestNotaX", estNota);
            message = message.replaceAll("XestFechaX", estFecha);

        } catch (IOException ex) {
            log.log(Level.SEVERE,"No se pudo leer el archivo. IO Exception: ", ex);
            throw ex;
        } catch (URISyntaxException ex) {
            log.log(Level.SEVERE,"No se pudo leer el archivo. URI Syntax Exception: ", ex);
            throw ex;
        }
        return message;
    }
}
