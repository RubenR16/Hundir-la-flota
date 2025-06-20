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
import javax.swing.UIManager;
import java.awt.Color;

public class Menu_principal extends JFrame {

    private String nombreUsuario;
    private JPanel contentPane;
    private final static int PORT = 5005;

    private JButton btnBPartida;
    private JButton btnCPartida;
    private JButton btnRank;
    private JButton btnStats;
    private JLabel lblBienvenido;

    private Locale currentLocale;
    private JButton btnAboutUs;

    public Menu_principal(String nombreUsuario, Locale locale) {
        this.nombreUsuario = nombreUsuario;
        this.currentLocale = locale;

        setTitle(Messages.labels().getString("label.TituloMenuPrincipal"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 379);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(238, 238, 238));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setResizable(false);
        setContentPane(contentPane);

        // Botón Stats personales
        btnStats = new JButton();
        btnStats.setBounds(272, 119, 204, 21);
        btnStats.addActionListener(e -> {
            Estadisticas_personales statsWindow = new Estadisticas_personales(nombreUsuario, currentLocale);
            statsWindow.setVisible(true);
        });
        contentPane.add(btnStats);

        // Botón Buscar partida (con diálogo IP)
        btnBPartida = new JButton();
        btnBPartida.setBounds(272, 86, 204, 21);
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
        lblBienvenido.setBounds(272, 22, 303, 13);
        contentPane.add(lblBienvenido);

        // Botón Ranking
        btnRank = new JButton();
        btnRank.setBounds(272, 152, 204, 21);
        btnRank.addActionListener(e -> {
            ConexionMySQL conexion = new ConexionMySQL();
            Connection conn = conexion.getCon();

            Rankings rankingWindow = new Rankings(conn, locale);
            rankingWindow.setVisible(true);
        });
        contentPane.add(btnRank);

        // Botón Crear partida
        btnCPartida = new JButton();
        btnCPartida.setBounds(272, 53, 204, 21);
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
        
        btnAboutUs = new JButton("About Us");
        btnAboutUs.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                JFrame ventanaAbout = new JFrame(Messages.labels().getString("aboutus.tituloVentana"));
        		ventanaAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		ventanaAbout.setSize(600, 400);
        		ventanaAbout.setLocationRelativeTo(null);
        		ventanaAbout.setContentPane(new AboutUs());// insertado el about
        		ventanaAbout.getSize(); // Ajusta la ventana al contenido
        		ventanaAbout.setResizable(false); // ❌ No se puede redimensionar
        		ventanaAbout.setLocationRelativeTo(null); // Centrada
        		ventanaAbout.setVisible(true);
        		}
        	});
        btnAboutUs.setBounds(272, 185, 204, 21);
        contentPane.add(btnAboutUs);

        // Finalmente, cargar los textos en el idioma actual
        switchLanguage(currentLocale);
    }

    private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        btnStats.setText(Messages.labels().getString("button.btnStats"));
        btnBPartida.setText(Messages.labels().getString("button.btnBPartida"));
        btnCPartida.setText(Messages.labels().getString("button.btnCPartida"));
        btnRank.setText(Messages.labels().getString("button.btnRank"));
        lblBienvenido.setText(Messages.labels().getString("label.lblBienvenido") + " " + nombreUsuario);
        btnAboutUs.setText(Messages.labels().getString("button.btnAboutUs"));
    }
}
