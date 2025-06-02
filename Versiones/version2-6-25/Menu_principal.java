package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import messages.Messages;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class Menu_principal extends JFrame {

    private String nombreUsuario;
    private JPanel contentPane;
    private final static int PORT = 5005;
    ServerSocket server;
    Socket client;
    Socket socket;
    private final static String SERVER = "";
    private JButton btnReanudar;
    private JButton btnBPartida;
    private JButton btnCPartida;
    private JButton btnRank;
    private JLabel lblBienvenido;
    private JButton btnStats;

    private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        btnStats.setText(Messages.labels().getString("button.btnStats"));
        btnReanudar.setText(Messages.labels().getString("button.btnReanudar"));
        btnBPartida.setText(Messages.labels().getString("button.btnBPartida"));
        btnCPartida.setText(Messages.labels().getString("button.btnCPartida"));
        btnRank.setText(Messages.labels().getString("button.btnRank"));
        lblBienvenido.setText(Messages.labels().getString("label.lblBienvenido") + " " + nombreUsuario);
    }

    public Menu_principal(String nombreUsuario, Locale locale) {

        setTitle("Menu Principal");
        this.nombreUsuario = nombreUsuario;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 379);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnStats = new JButton(Messages.labels().getString("button.btnStats"));
        btnStats.setBounds(288, 139, 172, 21);
        btnStats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Estadisticas_personales JFrame = new Estadisticas_personales(nombreUsuario);
                JFrame.setVisible(true);
            }
        });
        contentPane.add(btnStats);

        btnReanudar = new JButton(Messages.labels().getString("button.btnReanudar"));
        btnReanudar.setBounds(288, 106, 172, 21);
        btnReanudar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acción a definir
            }
        });
        contentPane.add(btnReanudar);

        btnBPartida = new JButton(Messages.labels().getString("button.btnBPartida"));
        btnBPartida.setBounds(288, 73, 172, 21);
        btnBPartida.addActionListener(e -> {
            JTextField ipField = new JTextField();
            Object[] message = {
                Messages.labels().getString("dialog.introduceIP"), ipField
            };
            Object[] opciones = {
                Messages.labels().getString("button.aceptar"),
                Messages.labels().getString("button.cancelar")
            };

            int opcion = JOptionPane.showOptionDialog(
                null,
                message,
                Messages.labels().getString("button.btnBPartida"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (opcion == JOptionPane.YES_OPTION) {
                String ip = ipField.getText();
                if (ip != null && !ip.trim().isEmpty()) {
                    new Thread(() -> {
                        try {
                            ClientePartida cliente = new ClientePartida();
                            cliente.conectarServidor(ip, PORT);
                            SwingUtilities.invokeLater(() -> new JuegoFrame(null, cliente, false));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(
                                null,
                                Messages.labels().getString("error.unirsePartida") + ": " + ex.getMessage()
                            );
                        }
                    }).start();
                }
            }
        });
        contentPane.add(btnBPartida);

        lblBienvenido = new JLabel(Messages.labels().getString("label.lblBienvenido") + " " + nombreUsuario);
        lblBienvenido.setBounds(288, 7, 256, 13);
        contentPane.add(lblBienvenido);

        btnRank = new JButton(Messages.labels().getString("button.btnRank"));
        btnRank.setBounds(288, 172, 172, 21);
        btnRank.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConexionMySQL c = new ConexionMySQL();
                Connection conn = c.getCon();
                Rankings rankingWindow = new Rankings(conn);
                rankingWindow.setVisible(true);
            }
        });
        contentPane.add(btnRank);

        btnCPartida = new JButton(Messages.labels().getString("button.btnCPartida"));
        btnCPartida.setBounds(288, 40, 172, 21);
        btnCPartida.addActionListener(e -> {
            JDialog ventanaEsperando = new JDialog();
            ventanaEsperando.setTitle(Messages.labels().getString("dialog.esperandoTitulo"));

            String mensaje = "<html><h3>" + Messages.labels().getString("dialog.tuIP") + ": " + VerIP.miIP() + "</h3><p>" + Messages.labels().getString("dialog.esperandoMensaje") + "</p></html>";
            JLabel contenido = new JLabel(mensaje, JLabel.CENTER);
            contenido.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

            ventanaEsperando.getContentPane().add(contenido);
            ventanaEsperando.pack();
            ventanaEsperando.setLocationRelativeTo(contentPane);
            ventanaEsperando.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            ventanaEsperando.setModal(false);
            ventanaEsperando.setVisible(true);

            new Thread(() -> {
                try {
                    ServidorPartida servidor = new ServidorPartida();
                    servidor.iniciarServidor(PORT);

                    SwingUtilities.invokeLater(() -> {
                        ventanaEsperando.dispose();
                        new JuegoFrame(servidor, null, true);
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        ventanaEsperando.dispose();
                        JOptionPane.showMessageDialog(null, Messages.labels().getString("error.crearPartida"));
                    });
                }
            }).start();
        });
        contentPane.add(btnCPartida);

        switchLanguage(locale);
    }
}
