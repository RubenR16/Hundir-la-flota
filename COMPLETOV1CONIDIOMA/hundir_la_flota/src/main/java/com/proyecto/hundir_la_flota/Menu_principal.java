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
import java.sql.Connection;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class Menu_principal extends JFrame {

    private String nombreUsuario;
    private JPanel contentPane;
    private final static int PORT = 5005;

    private JButton btnReanudar;
    private JButton btnBPartida;
    private JButton btnCPartida;
    private JButton btnRank;
    private JButton btnStats;
    private JLabel lblBienvenido;

    private Locale currentLocale;

    public Menu_principal(String nombreUsuario, Locale locale) {
        this.nombreUsuario = nombreUsuario;
        this.currentLocale = locale;

        setTitle(Messages.labels().getString("label.TituloMenuPrincipal"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 379);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Botón Stats personales
        btnStats = new JButton();
        btnStats.setBounds(272, 147, 204, 21);
        btnStats.addActionListener(e -> {
            Estadisticas_personales statsWindow = new Estadisticas_personales(nombreUsuario, currentLocale);
            statsWindow.setVisible(true);
        });
        contentPane.add(btnStats);

        // Botón Reanudar
        btnReanudar = new JButton();
        btnReanudar.setBounds(272, 114, 204, 21);
        btnReanudar.addActionListener(e -> {
            // Implementar acción de reanudar partida
        });
        contentPane.add(btnReanudar);

        // Botón Buscar partida (con diálogo IP)
        btnBPartida = new JButton();
        btnBPartida.setBounds(272, 81, 204, 21);
        btnBPartida.addActionListener(e -> {
            JTextField ipField = new JTextField();
            Object[] message = {
                Messages.labels().getString("dialog.introduceIP"), ipField
            };
            Object[] options = {
                Messages.labels().getString("button.aceptar"),
                Messages.labels().getString("button.cancelar")
            };

            int option = JOptionPane.showOptionDialog(
                null,
                message,
                Messages.labels().getString("button.btnBPartida"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (option == JOptionPane.YES_OPTION) {
                String ip = ipField.getText();
                if (ip != null && !ip.trim().isEmpty()) {
                    new Thread(() -> {
                        try {
                            ClientePartida cliente = new ClientePartida();
                            cliente.conectarServidor(ip, PORT);
                            SwingUtilities.invokeLater(() -> new JuegoFrame(null, cliente, false, nombreUsuario, locale));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                Messages.labels().getString("error.unirsePartida") + ": " + ex.getMessage());
                        }
                    }).start();
                }
            }
        });
        contentPane.add(btnBPartida);

        // Label Bienvenido
        lblBienvenido = new JLabel();
        lblBienvenido.setBounds(286, 12, 303, 13);
        contentPane.add(lblBienvenido);

        // Botón Ranking
        btnRank = new JButton();
        btnRank.setBounds(272, 180, 204, 21);
        btnRank.addActionListener(e -> {
            ConexionMySQL conexion = new ConexionMySQL();
            Connection conn = conexion.getCon();

            Rankings rankingWindow = new Rankings(conn, locale);
            rankingWindow.setVisible(true);
        });
        contentPane.add(btnRank);

        // Botón Crear partida
        btnCPartida = new JButton();
        btnCPartida.setBounds(272, 48, 204, 21);
        btnCPartida.addActionListener(e -> {
            JDialog waitingDialog = new JDialog();
            waitingDialog.setTitle(Messages.labels().getString("dialog.esperandoTitulo"));

            String message = "<html><h3>" + Messages.labels().getString("dialog.tuIP") + ": " + VerIP.miIP() + "</h3><p>" +
                             Messages.labels().getString("dialog.esperandoMensaje") + "</p></html>";
            JLabel contentLabel = new JLabel(message, JLabel.CENTER);
            contentLabel.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

            waitingDialog.getContentPane().add(contentLabel);
            waitingDialog.pack();
            waitingDialog.setLocationRelativeTo(contentPane);
            waitingDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            waitingDialog.setModal(false);
            waitingDialog.setVisible(true);

            new Thread(() -> {
                try {
                    ServidorPartida servidor = new ServidorPartida();
                    servidor.iniciarServidor(PORT);

                    SwingUtilities.invokeLater(() -> {
                        waitingDialog.dispose();
                        new JuegoFrame(servidor, null, true, nombreUsuario, locale);
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        waitingDialog.dispose();
                        JOptionPane.showMessageDialog(null, Messages.labels().getString("error.crearPartida"));
                    });
                }
            }).start();
        });
        contentPane.add(btnCPartida);

        // Finalmente, cargar los textos en el idioma actual
        switchLanguage(currentLocale);
    }

    private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        btnStats.setText(Messages.labels().getString("button.btnStats"));
        btnReanudar.setText(Messages.labels().getString("button.btnReanudar"));
        btnBPartida.setText(Messages.labels().getString("button.btnBPartida"));
        btnCPartida.setText(Messages.labels().getString("button.btnCPartida"));
        btnRank.setText(Messages.labels().getString("button.btnRank"));
        lblBienvenido.setText(Messages.labels().getString("label.lblBienvenido") + " " + nombreUsuario);
    }
}