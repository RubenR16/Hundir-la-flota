package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
	private final java.util.List<JButton> botones = new ArrayList<>();

	public ControlPanel(TableroPanel tablero) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(150, 400));

		JLabel label = new JLabel("Selecciona un barco:");
		add(label);

		// Botones para cada tipo de barco
		String[] nombres = {"Barco de 5", "Barco de 4", "Barco de 3 (1)", "Barco de 3 (2)", "Barco de 2"};
		for (int i = 0; i < nombres.length; i++) {
			int index = i;
			JButton b = new JButton(nombres[i]);
			b.setEnabled(false); // Solo se habilita el botón correspondiente
			b.addActionListener(e -> tablero.seleccionarBarco(index));
			botones.add(b);
			add(b);
		}

		// Botón para cambiar orientación del barco
		JButton rotar = new JButton("Rotar");
		rotar.addActionListener(e -> tablero.cambiarOrientacion());
		add(rotar);
	}

	// Habilita solo el botón del barco actual
	public void bloquearTodosMenos(int indexPermitido) {
		for (int i = 0; i < botones.size(); i++) {
			botones.get(i).setEnabled(i == indexPermitido);
		}
	}
}
