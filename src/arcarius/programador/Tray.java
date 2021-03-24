package arcarius.programador;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Jose Angeles
 * https://www.youtube.com/c/JoseAngelesSoftware?sub_confirmation=1
 */
public class Tray {

    /**
     *
     */
    private static frmPrincipal _Principal;
    private static clsCorreos Correos;

    /**
     *
     */
    public Tray() {
    }

    /**
     * Ejecutamos la apliacion corriendo un formulario principal
     * Posteriormente comenzamos a ejecutar un servicio en Thread
     * @param args
     */
    public static void main(String[] args) {
        //======================================================================
        if (SystemTray.isSupported()) {
            _Principal = new frmPrincipal();
            Correos = new clsCorreos(_Principal, _Principal.tfResumen);
            Correos.start();
            
            SystemTray systemTray = SystemTray.getSystemTray();
            Image imagen = new ImageIcon(Tray.class.getResource("logo32.jpg")).getImage();
            TrayIcon trayIcon = new TrayIcon(imagen, "Trabajando...");
            trayIcon.setImageAutoSize(true);
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Tray._Principal.setOpacity(1.0F);
                    trayIcon.displayMessage("Arcarius ERP 360", "Arcarius ERP Trabajando...", java.awt.TrayIcon.MessageType.INFO);
                }
            };
            trayIcon.addMouseListener(mouseAdapter);
            try {
                systemTray.add(trayIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
