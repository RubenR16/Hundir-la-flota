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

public class Ventana_partida extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana_partida frame = new Ventana_partida();
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
	public Ventana_partida() {
		setTitle("Partida");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 354, 489, 113);
		contentPane.add(textArea);
		
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
				panel2.setBounds(422, 20, 369, 297);
				panel2.setLayout(null);
				contentPane.add(panel2);

				ImageIcon img2 = new ImageIcon(getClass().getResource("/proyecto/barquitos2.jpg")); // Cambia por tu imagen
				Image esc2 = img2.getImage().getScaledInstance(panel2.getWidth(), panel2.getHeight(), Image.SCALE_SMOOTH);
				JLabel fondo2 = new JLabel(new ImageIcon(esc2));
				fondo2.setBounds(0, 0, panel2.getWidth(), panel2.getHeight());
				panel2.add(fondo2);
		
	
		
		textField = new JTextField();
		textField.setBounds(509, 446, 202, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnviar.setBounds(721, 446, 71, 21);
		contentPane.add(btnEnviar);
		
		JLabel lblCampoAlidado = new JLabel("Campo aliado");
		lblCampoAlidado.setBounds(180, 327, 133, 13);
		contentPane.add(lblCampoAlidado);
		
		JLabel lblCampoEnemigo = new JLabel("Campo enemigo");
		lblCampoEnemigo.setBounds(574, 327, 125, 13);
		contentPane.add(lblCampoEnemigo);
		
		
	}
}
