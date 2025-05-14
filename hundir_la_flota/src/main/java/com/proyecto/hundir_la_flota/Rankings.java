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
		
		JLabel lblNewLabel = new JLabel("RANKING");
		lblNewLabel.setBounds(312, 10, 88, 13);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(110, 75, 376, 197);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("2ND 444 MCK");
		lblNewLabel_2.setBounds(158, 40, 102, 34);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("1ST 44444 AAA");
		lblNewLabel_1.setBounds(158, 0, 89, 41);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("3ND 444 MCK");
		lblNewLabel_2_1.setBounds(158, 75, 127, 34);
		panel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("4ND 444 MCK");
		lblNewLabel_2_2.setBounds(158, 107, 127, 34);
		panel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("5ND 444 MCK");
		lblNewLabel_2_3.setBounds(158, 151, 127, 34);
		panel.add(lblNewLabel_2_3);
	}

}
