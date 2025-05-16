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
		this.nombreUsuario = nombreUsuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

		setBounds(100, 100, 770, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton btnNewButton = new JButton("Estadístias personales");
		btnNewButton.setBounds(288, 101, 172, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Reanudar partida");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(288, 70, 172, 21);
		contentPane.add(btnNewButton_1);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Buscar partida");
		btnNewButton_2.setBounds(288, 32, 172, 21);
		contentPane.add(btnNewButton_2);
		
		JLabel lblBienvenido = new JLabel("Bienvenido "+nombreUsuario);
		lblBienvenido.setBounds(288, 7, 256, 13);
		contentPane.add(lblBienvenido);
		
		JButton btnNewButton_3 = new JButton("Ranking");
		btnNewButton_3.setBounds(288, 133, 172, 21);
		contentPane.add(btnNewButton_3);
		
	}

}
