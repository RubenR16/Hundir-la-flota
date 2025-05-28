package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import messages.Messages;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Registrarse extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldUsuario;

	private static Connection con;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirmar;
	private Inicio_sesion inicioSesion;
	private JLabel lblRUser;
	private JLabel lblRContrasenya;
	private JButton btnConfirmar;
	private JLabel lblCContrasenya;
	private JButton btnCancelar;


	
	private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        lblRUser.setText(Messages.labels().getString("label.lblRUser"));
        lblRContrasenya.setText(Messages.labels().getString("label.lblRContrasenya"));
        btnConfirmar.setText(Messages.labels().getString("button.btnConfirmar"));
        btnCancelar.setText(Messages.labels().getString("button.btnCancelar"));
        lblCContrasenya.setText(Messages.labels().getString("label.lblCContrasenya"));
        
    }

	/**
	 * Create the frame.
	 */
	public Registrarse(Inicio_sesion inicio_sesion, Locale locale) {

		
		super(inicio_sesion, "Registrarse", true); // true = modal
		
		
		this.inicioSesion = inicio_sesion;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		
		ConexionMySQL c = new ConexionMySQL();
		con= c.getCon();		
		
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



		passwordField = new JPasswordField();
		passwordField.setBounds(334, 190, 153, 21);
		contentPane.add(passwordField);

		lblRUser = new JLabel("Usuario:");
		lblRUser.setBounds(334, 106, 153, 13);
		contentPane.add(lblRUser);

		lblRContrasenya = new JLabel("Contraseña:");
		lblRContrasenya.setBounds(334, 171, 153, 13);
		contentPane.add(lblRContrasenya);


		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String usuario = textFieldUsuario.getText().trim();
				String contraseña = new String(passwordField.getPassword());
				String confirmar = new String(passwordFieldConfirmar.getPassword());




				try {
					//INSERTAR usuarios nuevos
					PreparedStatement stmt = con.prepareStatement("INSERT INTO usuarios (nombre_usuario, password_usuario) VALUES (?, ?)");

					// Para comprobar si ya está el usuario en la base d datos
					PreparedStatement selectcomprobar = con.prepareStatement("SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?");


					selectcomprobar.setString(1, textFieldUsuario.getText());
					ResultSet rs = selectcomprobar.executeQuery();
					rs.next(); 


					if (rs.getInt(1) > 0) {
						JOptionPane.showMessageDialog(contentPane, Messages.errors().getString("error.alreadyExist"), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(textFieldUsuario.getText().length() > 20) {
						JOptionPane.showMessageDialog(contentPane,Messages.errors().getString("error.userContainMax20Char"), "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				
					

					if (textFieldUsuario.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane,Messages.errors().getString("error.userEmpty"), "Error", JOptionPane.ERROR_MESSAGE);
						return;


					} else {
						stmt.setString(1,textFieldUsuario.getText());

						if (contraseña.isEmpty()) {
							JOptionPane.showMessageDialog(contentPane,Messages.errors().getString("error.pswdEmpty"), "Error", JOptionPane.ERROR_MESSAGE);
							return;
   
						} else {
							stmt.setString(2,contraseña);}

						if (confirmar.isEmpty()) {
							JOptionPane.showMessageDialog(contentPane,Messages.errors().getString("error.pswdConfirm"), "Error", JOptionPane.ERROR_MESSAGE);
							return;

						} else if (!confirmar.equals(contraseña)) {
							JOptionPane.showMessageDialog(contentPane,Messages.errors().getString("error.pswdNotEqual"), "Error", JOptionPane.ERROR_MESSAGE);
							return;
						}



						String contrasenyaOriginal = contraseña;
						String contrasenyaCifrada = ContrasenyaCifrada.hashContrasenya(contrasenyaOriginal);

						stmt.setString(2, contrasenyaCifrada);

						stmt.executeUpdate();

						dispose();
						inicioSesion.dispose();

						Menu_principal menu = new Menu_principal(textFieldUsuario.getText());
						menu.setVisible(true);

					}

				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
			}
		});
		btnConfirmar.setBounds(443, 304, 117, 21);
		contentPane.add(btnConfirmar);

		lblCContrasenya = new JLabel("Confirmar contraseña:");
		lblCContrasenya.setBounds(334, 223, 153, 13);
		contentPane.add(lblCContrasenya);

		btnCancelar = new JButton("Cancelar");
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


		switchLanguage(locale);
		
	}
}
