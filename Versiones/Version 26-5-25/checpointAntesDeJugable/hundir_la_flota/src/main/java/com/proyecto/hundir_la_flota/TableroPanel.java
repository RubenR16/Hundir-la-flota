package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TableroPanel extends JPanel {
	private final int filas = 10, columnas = 10;
	private final Casilla[][] casillas = new Casilla[filas][columnas];

	private java.util.List<Barco> barcosColocados = new ArrayList<>();
	private int barcoSeleccionado = -1;         // Índice del barco que se está colocando
	private boolean horizontal = true;          // Orientación actual (horizontal o vertical)
	private int[] barcosPendientes = {5, 4, 3, 3, 2}; // Tamaños de los barcos por colocar
	private ControlPanel controlPanel;          // Referencia al panel de control

	// Colores asociados a cada tipo de barco
	private final Color[] coloresBarcos = {
		new Color(235, 152, 78),
		new Color(100, 255, 100),
		new Color(100, 100, 255),
		new Color(255, 255, 100),
		new Color(255, 150, 255)
	};

	public TableroPanel() {
		setLayout(new GridLayout(filas, columnas));

		// Crear cada casilla del tablero y agregar listeners de ratón
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				Casilla casilla = new Casilla(i, j);
				casilla.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						vistaPrevia(casilla.getFila(), casilla.getColumna()); // Muestra dónde iría el barco
					}

					@Override
					public void mouseExited(MouseEvent e) {
						limpiarVistaPrevia(); // Elimina la vista previa
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						colocarBarco(casilla.getFila(), casilla.getColumna()); // Intenta colocar un barco
					}
				});
				casillas[i][j] = casilla;
				add(casilla); // Añade al panel
			}
		}
		setPreferredSize(new Dimension(400, 400)); // Tamaño fijo del panel
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		controlPanel.bloquearTodosMenos(0); // Habilita solo el primer botón
		barcoSeleccionado = 0;
	}

	public void seleccionarBarco(int index) {
		if (index == barcoSeleccionado) {
			barcoSeleccionado = index;
		}
	}

	public void cambiarOrientacion() {
		horizontal = !horizontal;
	}

	private void vistaPrevia(int fila, int columna) {
		limpiarVistaPrevia();
		if (barcoSeleccionado >= barcosPendientes.length) return;

		int tamaño = barcosPendientes[barcoSeleccionado];

		for (int i = 0; i < tamaño; i++) {
			int f = fila + (horizontal ? 0 : i);
			int c = columna + (horizontal ? i : 0);
			if (f >= filas || c >= columnas) return;
			casillas[f][c].setPreview(true);
		}
	}

	private void limpiarVistaPrevia() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				casillas[i][j].setPreview(false);
			}
		}
	}

	private void colocarBarco(int fila, int columna) {
		if (barcoSeleccionado >= barcosPendientes.length) return;
		int tamaño = barcosPendientes[barcoSeleccionado];

		ArrayList<Casilla> posibles = new ArrayList<>();
		for (int i = 0; i < tamaño; i++) {
			int f = fila + (horizontal ? 0 : i);
			int c = columna + (horizontal ? i : 0);
			if (f >= filas || c >= columnas || casillas[f][c].tieneBarco()) return;
			posibles.add(casillas[f][c]);
		}

		for (Casilla casilla : posibles) {
			casilla.colocarBarco(coloresBarcos[barcoSeleccionado]);
		}

		barcosColocados.add(new Barco(tamaño, fila, columna, horizontal));
		barcoSeleccionado++;
		limpiarVistaPrevia();

		if (controlPanel != null) {
			controlPanel.bloquearTodosMenos(barcoSeleccionado);
		}

		if (barcoSeleccionado >= barcosPendientes.length) {
			JOptionPane.showMessageDialog(this, "Todos los barcos colocados");
		}
	}
}