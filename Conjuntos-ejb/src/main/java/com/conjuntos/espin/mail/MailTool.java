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

    public Boolean sendEmail(String receiver, String Nomnbre,String Lugar, String Asunto, String Forma, String Valor,String Fecha) {
        Boolean success = false;
        try {
            SendEmail sendEmail = new SendEmail();
            sendEmail.setDestinatario(receiver);
            sendEmail.setSubject("Notificación TU HOGAR");
            sendEmail.setSubject("Correo de Confirmación");
            init("TU HOGAR", "ningun link");
            sendEmail.setMensaje(getMessageComprobante(Nomnbre, Asunto, Lugar, Forma, Valor, Fecha));
            sendEmail.cargarConfiguracion();
            success = sendEmail.enviarCorreo();
        } catch (Exception ex) {
            log.log(Level.SEVERE,"No se envio el correo electrónico.", ex);
            success = false;
        }

        return success;
    }

    private String getMessageComprobante(String Nomnbre, String Asunto,String Lugar, String Forma, String Valor,String Fecha) throws Exception {
        String message = "";
        try {
            byte[] encoded = Files.readAllBytes(new File(MailTool.class.getResource(
                    "/com/conjuntos/espin/mail/resources/mail_cmp.html")
                    .toURI()).toPath());
            Charset encoding = Charset.forName("UTF-8");
            message = encoding.decode(ByteBuffer.wrap(encoded)).toString();
            message = message.replaceAll("XNombreX", Nomnbre);
            message = message.replaceAll("XLugarX", Lugar);
            message = message.replaceAll("XAsuntoX", Asunto);
            message = message.replaceAll("XFormaX", Forma);
            message = message.replaceAll("XValorX", Valor);
            message = message.replaceAll("XFechaX", Fecha);

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
