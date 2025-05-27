package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class Menu_principal extends JFrame {

	private String nombreUsuario;
	private JPanel contentPane;
	private final static int PORT = 5005;
	ServerSocket server;
	Socket client ;
	Socket socket ;
	private final static String SERVER = "";
	PrintStream output;

	public Menu_principal(String nombreUsuario) {
		
		setTitle("Menu Principal");
		this.nombreUsuario = nombreUsuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 770, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JButton btnStats = new JButton("Stats personales");
		btnStats.setBounds(288, 139, 172, 21);
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Estadisticas_personales JFrame = new Estadisticas_personales(nombreUsuario); 
				JFrame.setVisible(true);
				
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
		btnBPartida.addActionListener(e -> {
		    String ip = JOptionPane.showInputDialog("Introduce la IP del servidor:");
		    if (ip != null && !ip.trim().isEmpty()) {
		        new Thread(() -> {
		            try {
		                ClientePartida cliente = new ClientePartida();
		                cliente.conectarServidor(ip, PORT);
		                SwingUtilities.invokeLater(() -> new JuegoFrame(null, cliente, false));
		            } catch (IOException ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(null, "Error al unirse a la partida: " + ex.getMessage());
		            }
		        }).start();
		    }
		});
		btnBPartida.setBounds(288, 73, 172, 21);
		contentPane.add(btnBPartida);

		JLabel lblBienvenido = new JLabel("Bienvenido "+nombreUsuario);
		lblBienvenido.setBounds(288, 7, 256, 13);
		contentPane.add(lblBienvenido);

		JButton btnRank = new JButton("Ranking");
		btnRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConexionMySQL c = new ConexionMySQL();
		        Connection conn = c.getCon(); // o como sea tu método para obtener la conexión

		        Rankings rankingWindow = new Rankings(conn);
		        rankingWindow.setVisible(true);
			}
		});
		btnRank.setBounds(288, 172, 172, 21);
		contentPane.add(btnRank);

		JButton btnCPartida = new JButton("Crear partida");
		btnCPartida.addActionListener(e -> {
		    JDialog ventanaEsperando = new JDialog();
		    ventanaEsperando.setTitle("Esperando jugadores...");

		    String mensaje = "<html><h3>Tu IP es: " + VerIP.miIP() + "</h3><p>Esperando que se conecte un jugador...</p></html>";
		    JLabel contenido = new JLabel(mensaje, JLabel.CENTER);
		    contenido.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

		    ventanaEsperando.getContentPane().add(contenido);
		    ventanaEsperando.pack();
		    ventanaEsperando.setLocationRelativeTo(contentPane);
		    ventanaEsperando.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    ventanaEsperando.setModal(false);
		    ventanaEsperando.setVisible(true);

		    new Thread(() -> {
		        try {
		            ServidorPartida servidor = new ServidorPartida();
		            servidor.iniciarServidor(PORT);

		            SwingUtilities.invokeLater(() -> {
		                ventanaEsperando.dispose();
		                new JuegoFrame(servidor, null, true);
		            });

		        } catch (IOException ex) {
		            ex.printStackTrace();
		            SwingUtilities.invokeLater(() -> {
		                ventanaEsperando.dispose();
		                JOptionPane.showMessageDialog(null, "Error al crear la partida.");
		            });
		        }
		    }).start();
		});

		btnCPartida.setBounds(288, 40, 172, 21);
		contentPane.add(btnCPartida);
	}
}
