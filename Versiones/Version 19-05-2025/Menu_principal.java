package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Menu_principal extends JFrame {
	
	private String nombreUsuario;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public Menu_principal(String nombreUsuario) {
		setTitle("Menu Principal");
		this.nombreUsuario = nombreUsuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

		setBounds(100, 100, 770, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnStats = new JButton("Estadístias personales");
		btnStats.setBounds(288, 139, 172, 21);
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.setLayout(null);
		
		JButton btnReanudar = new JButton("Reanudar partida");
		btnReanudar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReanudar.setBounds(288, 106, 172, 21);
		contentPane.add(btnReanudar);
		contentPane.add(btnStats);
		
		JButton btnBPartida = new JButton("Buscar partida");
		btnBPartida.setBounds(288, 73, 172, 21);
		contentPane.add(btnBPartida);
		
		JLabel lblBienvenido = new JLabel("Bienvenido "+nombreUsuario);
		lblBienvenido.setBounds(288, 7, 256, 13);
		contentPane.add(lblBienvenido);
		
		JButton btnRank = new JButton("Ranking");
		btnRank.setBounds(288, 172, 172, 21);
		contentPane.add(btnRank);
		
		JButton btnCPartida = new JButton("Crear partida");
		btnCPartida.setBounds(288, 40, 172, 21);
		contentPane.add(btnCPartida);
		
	}
}
