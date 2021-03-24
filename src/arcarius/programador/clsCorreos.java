package arcarius.programador;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleContext;
import org.apache.commons.mail.EmailException;

/**
 * @author Jose Angeles
 *
 * Apliacion para correr en forma Tray.
 * https://www.youtube.com/c/JoseAngelesSoftware?sub_confirmation=1
 *
 *
 */
public class clsCorreos extends Thread {

    private JTextPane _Resumen;
    private frmPrincipal _parent;
    private Session _sesion = null;
    private int contador = 0;

    /**
     *
     * @param Principal
     * @param tfResumen
     */
    public clsCorreos(frmPrincipal Principal, javax.swing.JTextPane tfResumen) {
        _parent = Principal;
        _Resumen = tfResumen;
    }

    public void run() {
        try {
            CrearSesion();
            _resumen(Color.red, "Creamos sesion");
            while (true) {
                contador++;
                EnviaHTML(contador);
                _resumen(Color.BLUE, "Mandamos correo");
                if (contador >= 2) {
                    break;
                }
                Thread.sleep(5000);
            }
        } catch (EmailException ex) {
            Logger.getLogger(clsCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(clsCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(clsCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(clsCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void _resumen(Color c, String s) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(javax.swing.text.SimpleAttributeSet.EMPTY, javax.swing.text.StyleConstants.Foreground, c);
        int len = _Resumen.getDocument().getLength();
        _Resumen.setCaretPosition(len);
        _Resumen.setCharacterAttributes(aset, false);
        _Resumen.replaceSelection(s + "\n");
    }

    private void conectar() {
        try {
            while (true) {
                System.out.println("Hola");
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(clsCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void CrearSesion() {
        Properties props = new Properties();
        final String user = "tucorreo@dominio.com.mx";
        final String pass = "tupassword";

        javax.mail.Authenticator auth = new javax.mail.Authenticator() {
            @Override
            public javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(user, pass);
            }
        };
        props.put("mail.smtp.host", "mail.dominio.com.mx");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        this._sesion = Session.getDefaultInstance(props, auth);
    }

    public void EnviaHTML(int numero) throws EmailException, MalformedURLException, MessagingException, IOException {
        MimeMessage Correo = new MimeMessage(this._sesion);
        Correo.setSubject("Prueba de System Tray");
        Correo.setFrom("tucorreo@dominio.com.mx");
        Correo.setRecipients(Message.RecipientType.TO, "destinatario@dominio.com.mx");
        MimeBodyPart ContenidoCuerpo = new MimeBodyPart();
        ContenidoCuerpo.setText("<h1>Hola mundo Arcarius Java</h1><br><h3>Contador: " + numero + "</h3>", "UTF-8", "html");
        MimeMultipart Contenido = new MimeMultipart();
        Contenido.addBodyPart(ContenidoCuerpo);
        Correo.setContent(Contenido);
        Transport.send(Correo);
    }

}
