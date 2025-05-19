package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Estadisticas_personales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Estadisticas_personales frame = new Estadisticas_personales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Estadisticas_personales() {
		setTitle("Estadisticas Personales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPartidasJugadas = new JLabel("Partidas jugadas");
		lblPartidasJugadas.setBounds(113, 42, 101, 17);
		contentPane.add(lblPartidasJugadas);
		
		JLabel lblBarcosHundidos = new JLabel("Barcos hundidos");
		lblBarcosHundidos.setBounds(113, 67, 99, 17);
		contentPane.add(lblBarcosHundidos);
		
		JLabel lblPuntos = new JLabel("Puntos totales");
		lblPuntos.setBounds(113, 96, 86, 17);
		contentPane.add(lblPuntos);
		
		JLabel lblCasillasAgua = new JLabel("Casillas de agua golpeadas");
		lblCasillasAgua.setBounds(113, 121, 162, 17);
		contentPane.add(lblCasillasAgua);
		
		JLabel lblNumPartidas = new JLabel("1");
		lblNumPartidas.setBounds(291, 42, 60, 17);
		contentPane.add(lblNumPartidas);
		
		JLabel lblNumBarcos = new JLabel("4");
		lblNumBarcos.setBounds(291, 67, 60, 17);
		contentPane.add(lblNumBarcos);
		
		JLabel lblNumPuntos = new JLabel("1300");
		lblNumPuntos.setBounds(291, 96, 60, 17);
		contentPane.add(lblNumPuntos);
		
		JLabel lblNumCasillasAgua = new JLabel("15");
		lblNumCasillasAgua.setBounds(291, 121, 60, 17);
		contentPane.add(lblNumCasillasAgua);
		
		JLabel lblUsuario = new JLabel("Usuario123");
		lblUsuario.setBounds(189, 13, 148, 17);
		contentPane.add(lblUsuario);
	}

}
