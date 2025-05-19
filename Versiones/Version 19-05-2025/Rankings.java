package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class Rankings extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rankings frame = new Rankings();
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
	public Rankings() {
		setTitle("Ranking");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRanking = new JLabel("RANKING");
		lblRanking.setBounds(312, 10, 88, 13);
		contentPane.add(lblRanking);
		
		JPanel panel = new JPanel();
		panel.setBounds(110, 75, 376, 197);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblPrimero = new JLabel("2ND 444 MCK");
		lblPrimero.setBounds(158, 40, 102, 34);
		panel.add(lblPrimero);
		
		JLabel lblSegundo = new JLabel("1ST 44444 AAA");
		lblSegundo.setBounds(158, 0, 89, 41);
		panel.add(lblSegundo);
		
		JLabel lblTercero = new JLabel("3ND 444 MCK");
		lblTercero.setBounds(158, 75, 127, 34);
		panel.add(lblTercero);
		
		JLabel lblCuarto = new JLabel("4ND 444 MCK");
		lblCuarto.setBounds(158, 107, 127, 34);
		panel.add(lblCuarto);
		
		JLabel lblQuinto = new JLabel("5ND 444 MCK");
		lblQuinto.setBounds(158, 151, 127, 34);
		panel.add(lblQuinto);
	}

}
