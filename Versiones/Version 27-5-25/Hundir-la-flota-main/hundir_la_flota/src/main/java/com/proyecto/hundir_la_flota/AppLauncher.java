package com.proyecto.hundir_la_flota;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Ventana dummy para ser el padre
                JFrame dummy = new JFrame();
                dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Inicio_sesion login = new Inicio_sesion(dummy);
                login.setVisible(true); // Modal: bloquea aquí

                if (login.isSesionIniciada()) {
                    SwingUtilities.invokeLater(() -> {
                        Menu_principal menu = new Menu_principal(login.getUsuario());
                        menu.setVisible(true);
                        dummy.dispose(); // cierra el dummy
                    });
                } else {
                    // Si no se inicia sesión, cierra la app
                    dummy.dispose();
                   // System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}