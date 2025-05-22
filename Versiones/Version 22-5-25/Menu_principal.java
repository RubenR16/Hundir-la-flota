package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Menu_principal extends JFrame {

	private String nombreUsuario;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final static int PORT = 5005;
	ServerSocket server;
	Socket client ;
	Socket socket ;
	private final static String SERVER = "10.2.1.164";
	PrintStream output;






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
		btnBPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBPartida.setBounds(288, 73, 172, 21);
		contentPane.add(btnBPartida);

		JLabel lblBienvenido = new JLabel("Bienvenido "+nombreUsuario);
		lblBienvenido.setBounds(288, 7, 256, 13);
		contentPane.add(lblBienvenido);

		JButton btnRank = new JButton("Ranking");
		btnRank.setBounds(288, 172, 172, 21);
		contentPane.add(btnRank);










		JButton btnCPartida = new JButton("Crear partida");
		btnCPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerSocket server = new ServerSocket(PORT);
					System.out.println("Server started");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				

				JDialog ventanaEsperando = new JDialog();
				ventanaEsperando.setTitle("Esperando jugadores...");

				Icon gifIcon = new ImageIcon(getClass().getResource("cargando.gif"));
				String mensaje = "<html><h3> Tu IP es: " + VerIP.miIP() + "</h3></html>";
				JLabel contenido = new JLabel(mensaje, gifIcon, JLabel.LEFT);
				contenido.setIconTextGap(5); // espacio entre el gif y el texto
				contenido.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

				ventanaEsperando.getContentPane().add(contenido);
				ventanaEsperando.pack(); // ajusta el tamaño al contenido
				ventanaEsperando.setLocationRelativeTo(contentPane); // lo centra en nuestra ventana
				ventanaEsperando.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				ventanaEsperando.setVisible(true);

				// ventanaEsperando.dispose(); para cerrarlo
			}

		});
		btnCPartida.setBounds(288, 40, 172, 21);
		contentPane.add(btnCPartida);

	}
}
