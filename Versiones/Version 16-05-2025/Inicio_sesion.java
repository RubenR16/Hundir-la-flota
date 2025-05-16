package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Inicio_sesion extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private final String URL= "jdbc:mysql://localhost:33306/hundir_la_flota";
	private final String USER="root";
	private final String PASSWORD="alumnoalumno";
	private static Connection connection;
	private static PreparedStatement stmt;
	private String usuario;
	private String contr;
	private boolean sesionIniciada = false;
	private JPasswordField passwordField;

	
	
	
	public boolean isSesionIniciada() {
	    return sesionIniciada;
	    
	    
	}
	
	public String getUsuario() {
	    return usuario;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				EventQueue.invokeLater(() -> {
				try {
					
					 // Ventana dummy para ser el padre
			        JFrame dummy = new JFrame();
			        dummy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			        Inicio_sesion login = new Inicio_sesion(dummy);
			        login.setVisible(true); // Modal: bloquea aquí

			        if (login.isSesionIniciada()) {
			        	SwingUtilities.invokeLater(() -> {
		                    Menu_principal menu = new Menu_principal(login.getUsuario());// puedes pasar el nombre si quieres
		                    menu.setVisible(true);// JFrame: no bloquea nada
		                });
			  
			        } else {
			           // System.exit(0); // No inició sesión → cerrar app
			        }
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				});//
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inicio_sesion(JFrame Menu_principal) {
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		setTitle("Iniciar sesion");
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		
		
		JLabel lblNewLabelUser = new JLabel("Usuario:");
		lblNewLabelUser.setBounds(334, 142, 153, 13);
		contentPane.add(lblNewLabelUser);
		
		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(334, 192, 88, 13);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Registarse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Registrarse dialog = new Registrarse(Inicio_sesion.this); 
		        dialog.setVisible(true);
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(334, 214, 153, 21);
		contentPane.add(passwordField);
		
		String contraseña = new String(passwordField.getPassword());
		
		btnNewButton.setBounds(263, 261, 117, 21);
		contentPane.add(btnNewButton);

		//BOTON INICIAR SESIÓN
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean existe = false;
				boolean coincidepasswd = false;
				
				
				
				usuario = textFieldUsuario.getText();


				try {
					stmt = connection.prepareStatement("select * from usuarios where nombre_usuario = ?");
					
					stmt.setString(1,textFieldUsuario.getText());
					
					ResultSet rs = stmt.executeQuery();

					//SI EXISTE EL USUARIO EN LA BDD EL BOOLEANO PASA A ESTAR EN TRUE,
					//SI EXISTE COMPRUEBA SI LA CONTRASEÑA PROPORCIONADA EN EL TEXTFIELD 
					//Y SI COINCIDE SE PONE A TRUE SU BOOLEANO 
					if (rs.next()) {
						existe=true;
					
						String contrasenyaOriginal = new String(passwordField.getPassword());
			            String contrasenyaCifrada = ContrasenyaCifrada.hashContrasenya(contrasenyaOriginal);

						
						if(contrasenyaCifrada.equals(rs.getString("password_usuario"))){
								coincidepasswd=true;
								
							}	
					}
						
					//NO EXISTE EL USUARIO = ERROR
					if(!existe) {
					
						JOptionPane.showMessageDialog(contentPane,"Error: Usuario no existente","Error",JOptionPane.ERROR_MESSAGE);
					}else if(coincidepasswd){ //SI EXISTE EL USUARIO COMPRUEBA QUE COINCIDA LA PASSWORD Y INICIA SESIÓN
						sesionIniciada = true;
						Menu_principal menu = new Menu_principal(textFieldUsuario.getText());
						menu.setVisible(true);
						dispose();
						
					}else { // SI NO COICIDE LA PASSWORD SALTA MENSAJITO ERROR
					
						JOptionPane.showMessageDialog(contentPane,"Contraseña incorrecta","Error",JOptionPane.ERROR_MESSAGE);
	
					}
					
					
					
				}catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnIniciarSesion.setBounds(408, 261, 117, 21);
		contentPane.add(btnIniciarSesion);
		
		
		
		//codigo para menu principal (se abre auto al abrir otra)
		
		//Inicio_sesion dialog = new Inicio_sesion(dialog Menu_principal);
		//dialog.setVisible(true);
		
	}
}
