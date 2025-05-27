package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class JuegoFrame extends JFrame {
    private TableroPanel tableroPanel;
    protected ControlPanel controlPanel;
    private ServidorPartida servidor;
    private ClientePartida cliente;
    private boolean esServidor;
    private JButton btnListo;

    // Constructor original para compatibilidad
    public JuegoFrame() {
        this(null, null, false);
    }

    // Constructor con parámetros de red
    public JuegoFrame(ServidorPartida servidor, ClientePartida cliente, boolean esServidor) {
        this.servidor = servidor;
        this.cliente = cliente;
        this.esServidor = esServidor;
        
        setTitle("Hundir la Flota - Colocación de Barcos");
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
        btnListo = new JButton("¡Listo para jugar!");
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
        JOptionPane.showMessageDialog(this, "Todos los barcos colocados. ¡Haz clic en 'Listo para jugar!' cuando estés preparado!");
    }

    private void iniciarPartida() {
        List<Barco> barcosColocados = tableroPanel.getListaBarcos();
        
        if (barcosColocados.size() != 5) {
            JOptionPane.showMessageDialog(this, "Debes colocar todos los barcos antes de continuar.");
            return;
        }

        // Cerrar esta ventana
        this.dispose();

        // Crear y mostrar PartidaFrame
        SwingUtilities.invokeLater(() -> {
            PartidaFrame partidaFrame = new PartidaFrame(barcosColocados, esServidor, servidor, cliente);
            partidaFrame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JuegoFrame::new);
    }
}
