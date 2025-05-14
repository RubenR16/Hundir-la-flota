package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana_preparacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana_preparacion frame = new Ventana_preparacion();
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
	public Ventana_preparacion() {
		setTitle("Preparación");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Panel 1
				JPanel panel1 = new JPanel();
				panel1.setBounds(10, 20, 402, 297);
				panel1.setLayout(null); // Para que el JLabel con imagen se ajuste manualmente
				contentPane.add(panel1);

				ImageIcon img1 = new ImageIcon(getClass().getResource("/proyecto/barquitos.jpg")); // Cambia por tu imagen
				Image esc1 = img1.getImage().getScaledInstance(panel1.getWidth(), panel1.getHeight(), Image.SCALE_SMOOTH);
				JLabel fondo1 = new JLabel(new ImageIcon(esc1));
				fondo1.setBounds(0, 0, panel1.getWidth(), panel1.getHeight());
				panel1.add(fondo1);

				// Panel 2
				JPanel panel2 = new JPanel();
				panel2.setBounds(422, 20, 246, 297);
				panel2.setLayout(null);
				contentPane.add(panel2);

				ImageIcon img2 = new ImageIcon(getClass().getResource("/proyecto/images.jpg")); // Cambia por tu imagen
				Image esc2 = img2.getImage().getScaledInstance(panel2.getWidth(), panel2.getHeight(), Image.SCALE_SMOOTH);
				JLabel fondo2 = new JLabel(new ImageIcon(esc2));
				fondo2.setBounds(0, 0, panel2.getWidth(), panel2.getHeight());
				panel2.add(fondo2);
		
		JLabel lblNewLabel = new JLabel("Campo aliado");
		lblNewLabel.setBounds(180, 327, 133, 13);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Listo");
		btnNewButton.setBounds(290, 365, 133, 56);
		contentPane.add(btnNewButton);
		
		
	}
}
