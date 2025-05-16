package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Registrarse extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;

	private final String URL= "jdbc:mysql://localhost:33306/hundir_la_flota";
	private final String USER="root";
	private final String PASSWORD="alumnoalumno";
	private static Connection connection;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirmar;
	private Inicio_sesion inicioSesion;

	
	/**
	 * Create the frame.
	 */
	public Registrarse(Inicio_sesion inicio_sesion) {
		super(inicio_sesion, "Registrarse", true); // true = modal
		this.inicioSesion = inicio_sesion;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setTitle("Registrarse");
		setBounds(100, 100, 830, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		contentPane.setLayout(null);

		textFieldUsuario = new JTextField();
		textFieldUsuario.setToolTipText("");
		textFieldUsuario.setBounds(334, 129, 153, 19);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);


		
		/////////
		passwordField = new JPasswordField();
		passwordField.setBounds(334, 190, 153, 21);
		contentPane.add(passwordField);

		JLabel lblNewLabelUser = new JLabel("Usuario:");
		lblNewLabelUser.setBounds(334, 106, 153, 13);
		contentPane.add(lblNewLabelUser);

		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(334, 171, 153, 13);
		contentPane.add(lblNewLabel);


		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usuario = textFieldUsuario.getText().trim();
				String contraseña = new String(passwordField.getPassword());
				String confirmar = new String(passwordFieldConfirmar.getPassword());
				
				


				try {
					//INSERTAR usuarios nuevos
					PreparedStatement stmt = connection.prepareStatement("INSERT INTO usuarios (nombre_usuario, password_usuario) VALUES (?, ?)");

					// Para comprobar si ya está el usuario en la base d datos
					PreparedStatement selectcomprobar = connection.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?");
					
					
					selectcomprobar.setString(1, textFieldUsuario.getText());
					ResultSet rs = selectcomprobar.executeQuery();
					rs.next(); 
					
					
					if (rs.getInt(1) > 0) {
						JOptionPane.showMessageDialog(contentPane, "Ese nombre de usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					if (textFieldUsuario.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Inserta tu nombre para continuar", "Error", JOptionPane.ERROR_MESSAGE);
						return;


					} else {
						stmt.setString(1,textFieldUsuario.getText());

						if (contraseña.isEmpty()) {
							JOptionPane.showMessageDialog(contentPane, "Tienes que añadir una contraseña", "Error", JOptionPane.ERROR_MESSAGE);
							return;

						} else {
							stmt.setString(2,contraseña);}

						if (confirmar.isEmpty()) {
							JOptionPane.showMessageDialog(contentPane, "Confirma la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
							return;

						} else if (!confirmar.equals(contraseña)) {
							JOptionPane.showMessageDialog(contentPane, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						

						String contrasenyaOriginal = contraseña;
			            String contrasenyaCifrada = ContrasenyaCifrada.hashContrasenya(contrasenyaOriginal);

			            stmt.setString(2, contrasenyaCifrada);
			            
						stmt.executeUpdate();
						
						dispose();
						inicioSesion.dispose();

						Menu_principal menu = new Menu_principal(textFieldUsuario.getText());
						JLabel lblBienvenido = new JLabel("Bienvenido"+usuario);
						menu.setVisible(true);
						
						}
					
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(443, 304, 117, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Confirmar contraseña:");
		lblNewLabel_1.setBounds(334, 223, 153, 13);
		contentPane.add(lblNewLabel_1);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		btnCancelar.setBounds(259, 304, 117, 21);
		contentPane.add(btnCancelar);
		
		passwordFieldConfirmar = new JPasswordField();
		passwordFieldConfirmar.setBounds(334, 248, 153, 21);
		contentPane.add(passwordFieldConfirmar);
		

	}
}