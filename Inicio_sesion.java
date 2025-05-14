package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Inicio_sesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private JTextField textFieldContraseña;
	private final String URL= "jdbc:mysql://localhost:33306/hundir_la_flota";
	private final String USER="root";
	private final String PASSWORD="alumnoalumno";
	private static Connection connection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio_sesion frame = new Inicio_sesion();
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
	public Inicio_sesion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		setTitle("Iniciar sesion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setToolTipText("");
		textFieldUsuario.setBounds(334, 163, 153, 19);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		textFieldContraseña = new JTextField();
		textFieldContraseña.setBounds(334, 215, 153, 19);
		contentPane.add(textFieldContraseña);
		textFieldContraseña.setColumns(10);
		
		JLabel lblNewLabelUser = new JLabel("Usuario:");
		lblNewLabelUser.setBounds(334, 142, 153, 13);
		contentPane.add(lblNewLabelUser);
		
		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(334, 192, 88, 13);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Registarse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PreparedStatement stmt = connection.prepareStatement("INSERT INTO usuarios (nombre_usuario, password_usuario) VALUES (?, ?)");

					stmt.setString(1,textFieldUsuario.getText());//1 specifies the first parameter in the query  
					stmt.setString(2,textFieldContraseña.getText());
					stmt.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(263, 261, 117, 21);
		contentPane.add(btnNewButton);
		
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnIniciarSesion.setBounds(408, 261, 117, 21);
		contentPane.add(btnIniciarSesion);
		
		JButton btnNewButton_1 = new JButton("Modo invitado");
		btnNewButton_1.setBounds(343, 307, 117, 21);
		contentPane.add(btnNewButton_1);
	}
}
