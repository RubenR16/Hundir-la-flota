package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;

public class Casilla extends JPanel {
	private final int fila, columna;
	private boolean tieneBarco = false;
	private boolean esPreview = false;

	public Casilla(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		setBackground(Color.CYAN); // Color de mar
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public int getFila() { return fila; }
	public int getColumna() { return columna; }
	public boolean tieneBarco() { return tieneBarco; }

	public void colocarBarco(Color color) {
		tieneBarco = true;
		setBackground(color); // Cambia el color para indicar barco colocado
	}

	public void setPreview(boolean preview) {
		if (!tieneBarco) {
			esPreview = preview;
			setBackground(preview ? Color.LIGHT_GRAY : Color.CYAN); // Indica vista previa
		}
	}
}
