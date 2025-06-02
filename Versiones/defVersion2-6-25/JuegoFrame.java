package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import messages.Messages;

public class JuegoFrame extends JFrame {
    private TableroPanel tableroPanel;
    private ControlPanel controlPanel;
    private ServidorPartida servidor;
    private ClientePartida cliente;
    private boolean esServidor;
    private JButton btnListo;
    private String nombreUsuario;
    private Locale locale;

    // Constructor original para compatibilidad
    public JuegoFrame() {
        this(null, null, false, "", Locale.getDefault());
    }

    // Constructor con parámetros de red y locale
    public JuegoFrame(ServidorPartida servidor, ClientePartida cliente, boolean esServidor, String nombreUsuario, Locale locale) {
        this.servidor = servidor;
        this.cliente = cliente;
        this.esServidor = esServidor;
        this.nombreUsuario = nombreUsuario;
        this.locale = locale;

        // Cargar el idioma
        Messages.loadLocale(locale);

        setTitle(Messages.labels().getString("label.tituloColocacion"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Crear el panel del tablero donde se colocan los barcos
        tableroPanel = new TableroPanel();

        // Crear el panel de control con botones para seleccionar barcos y rotar
        controlPanel = new ControlPanel(tableroPanel);

        // Establecer comunicación entre el tablero y el panel de control
        tableroPanel.setControlPanel(controlPanel);
        tableroPanel.setJuegoFrame(this);

        // Botón para confirmar que está listo
        btnListo = new JButton(Messages.labels().getString("label.btnListo"));
        btnListo.setEnabled(false);
        btnListo.addActionListener(e -> iniciarPartida());

        // Panel inferior con el botón
        JPanel panelInferior = new JPanel();
        panelInferior.add(btnListo);

        // Añadir los paneles a la ventana
        getContentPane().add(tableroPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.EAST);
        getContentPane().add(panelInferior, BorderLayout.SOUTH);

        // Ajustar tamaño y mostrar ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void todosLosBarcosColocados() {
        btnListo.setEnabled(true);
        JOptionPane.showMessageDialog(this, Messages.labels().getString("label.msgTodosColocados"));
    }

    private void iniciarPartida() {
        List<Barco> barcosColocados = tableroPanel.getListaBarcos();
        
        if (barcosColocados.size() != 5) {
            JOptionPane.showMessageDialog(this, Messages.labels().getString("label.msgFaltanBarcos"));
            return;
        }

        // Cerrar esta ventana
        this.dispose();

        // Crear y mostrar PartidaFrame
        SwingUtilities.invokeLater(() -> {
            PartidaFrame partidaFrame = new PartidaFrame(barcosColocados, esServidor, servidor, cliente, nombreUsuario, locale);
            partidaFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoFrame(null, null, false, "", Locale.getDefault()));
    }
}
