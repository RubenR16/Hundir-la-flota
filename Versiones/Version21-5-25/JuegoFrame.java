package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;

public class JuegoFrame extends JFrame {
	private TableroPanel tableroPanel;
	private ControlPanel controlPanel;

	public JuegoFrame() {
		setTitle("Hundir la Flota - Colocación de Barcos");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Crear el panel del tablero donde se colocan los barcos
		tableroPanel = new TableroPanel();

		// Crear el panel de control con botones para seleccionar barcos y rotar
		controlPanel = new ControlPanel(tableroPanel);

		// Establecer comunicación entre el tablero y el panel de control
		tableroPanel.setControlPanel(controlPanel);

		// Añadir los paneles a la ventana
		add(tableroPanel, BorderLayout.CENTER);   // Panel principal del tablero
		add(controlPanel, BorderLayout.EAST);     // Panel lateral de controles

		// Ajustar tamaño y mostrar ventana
		pack();
		setLocationRelativeTo(null); // Centra la ventana
		setVisible(true);
	}

	public static void main(String[] args) {
		// Iniciar la interfaz gráfica en el hilo de eventos de Swing
		SwingUtilities.invokeLater(JuegoFrame::new);
	}
}
