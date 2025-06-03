package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import messages.Messages;

public class ControlPanel extends JPanel {
	private final List<JButton> botones = new ArrayList<>();

	public ControlPanel(TableroPanel tablero) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(150, 400));

		JLabel label = new JLabel(Messages.labels().getString("label.seleccionaBarco"));
		add(label);

		// Botones para cada tipo de barco
		String[] nombres = {
			Messages.labels().getString("label.barco5"),
			Messages.labels().getString("label.barco4"),
			Messages.labels().getString("label.barco3a"),
			Messages.labels().getString("label.barco3b"),
			Messages.labels().getString("label.barco2")
		};

		for (int i = 0; i < nombres.length; i++) {
			int index = i;
			JButton b = new JButton(nombres[i]);
			b.setEnabled(false); // Solo se habilita el botón correspondiente
			b.addActionListener(e -> tablero.seleccionarBarco(index));
			botones.add(b);
			add(b);
		}

		// Botón para cambiar orientación del barco
		JButton rotar = new JButton(Messages.labels().getString("label.rotar"));
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
