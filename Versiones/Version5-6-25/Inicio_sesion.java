package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import messages.Messages;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Inicio_sesion extends JDialog {

	private JPanel contentPane;
	private JTextField textFieldUsuario;
	private static Connection connection;
	private static PreparedStatement stmt;
	private String usuario;
	private String contr;
	private boolean sesionIniciada = false;
	private JPasswordField passwordField;
	private JButton submitButton;
    private JMenuBar menuBar;
    private JLabel lblUsuario;
    private JLabel lblContrasenya;
    private JButton btnRegistrarse;
    private JButton btnIniciarSesion;
    private JMenuBar menuBar_1;
    private JMenu menuIdiomas;
    private Locale currentLocale = new Locale("es", "ES"); // Español por defecto
    private JMenuItem miEspanyol;
    private JMenuItem miIngles;



	
	
	
	public boolean isSesionIniciada() {
	    return sesionIniciada;
	    
	    
	}
	
	public String getUsuario() {
		
	    return usuario;
	}
	

	
	private void switchLanguage(Locale locale) {
			currentLocale = locale;
	        Messages.loadLocale(locale);
	        lblUsuario.setText(Messages.labels().getString("label.lblUsuario"));
	        lblContrasenya.setText(Messages.labels().getString("label.lblContrasenya"));
	        btnRegistrarse.setText(Messages.labels().getString("button.btnRegistrarse"));
	        btnIniciarSesion.setText(Messages.labels().getString("button.btnIniciarSesion"));
	        menuIdiomas.setText(Messages.labels().getString("label.MenuIdiomas"));
	        miEspanyol.setText(Messages.labels().getString("menuItem.espanyol"));
	        miIngles.setText(Messages.labels().getString("menuItem.ingles"));
	    }
	
	private void iniciarSesion() {
		
		boolean existe = false;
		boolean coincidepasswd = false;
		
		
		
		usuario = textFieldUsuario.getText();
		
		// Ejecutar procedimiento para levantar baneos expirados
		
		try (PreparedStatement call = connection.prepareStatement("CALL levantar_baneos_expirados()")) {
		    call.execute();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}



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
						
						boolean estaBaneado = rs.getBoolean("baneado");
	                    if (estaBaneado) {
	                    	Object[] options = {
	                    		Messages.labels().getString("button.aceptar")
	                    	};
	                    	JOptionPane.showOptionDialog(
	                    		contentPane,
	                    		Messages.errors().getString("error.banned"),
	                    		"BANEADO",
	                    		JOptionPane.DEFAULT_OPTION,
	                    		JOptionPane.ERROR_MESSAGE,
	                    		null,
	                    		options,
	                    		options[0]
	                    	);
	                        return;
	                    }
						
					}	
			}
				
			//NO EXISTE EL USUARIO = ERROR
			if(!existe) {
				Object[] options = {
					Messages.labels().getString("button.aceptar")
				};
				JOptionPane.showOptionDialog(
					contentPane,
					Messages.errors().getString("error.userNExist"),
					"Error",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]
				);
			}else if(coincidepasswd){ //SI EXISTE EL USUARIO COMPRUEBA QUE COINCIDA LA PASSWORD Y INICIA SESIÓN
				sesionIniciada = true;
				Menu_principal menu = new Menu_principal(textFieldUsuario.getText(), currentLocale);
				menu.setVisible(true);
				dispose();
				
			}else { // SI NO COICIDE LA PASSWORD SALTA MENSAJITO ERROR
				Object[] options = {
					Messages.labels().getString("button.aceptar")
				};
				JOptionPane.showOptionDialog(
					contentPane,
					Messages.errors().getString("error.wrongPswd"),
					"Error",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null,
					options,
					options[0]
				);

			}
			
			
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}


	 

	/**
	 * Create the frame.
	 */
	public Inicio_sesion(JFrame Menu_principal) {
		
		setTitle("Iniciar Sesión");
		
		ConexionMySQL c = new ConexionMySQL();
		
		connection = c.getCon();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 855, 452);
		
		setResizable(false);
		
		menuBar_1 = new JMenuBar();
		setJMenuBar(menuBar_1);
		
		menuIdiomas = new JMenu("Idiomas");
		menuBar_1.add(menuIdiomas);
		
		miEspanyol = new JMenuItem("Español");
		miEspanyol.addActionListener(e -> switchLanguage(new Locale("es", "ES")));
		
		
		miIngles = new JMenuItem("Inglés");
		
		miIngles.addActionListener(e -> switchLanguage(new Locale("en", "US")));
		
		menuIdiomas.add(miEspanyol);
		menuIdiomas.add(miIngles);
		
		
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setToolTipText("");
		textFieldUsuario.setBounds(334, 163, 153, 19);
		contentPane.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(334, 142, 153, 13);
		contentPane.add(lblUsuario);
		
		lblContrasenya = new JLabel("Contraseña:");
		lblContrasenya.setBounds(334, 192, 88, 13);
		contentPane.add(lblContrasenya);
		
		btnRegistrarse = new JButton("Registarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Registrarse dialog = new Registrarse(Inicio_sesion.this,currentLocale); 
		        dialog.setVisible(true);
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		});
		passwordField.setBounds(334, 214, 153, 21);
		contentPane.add(passwordField);
		
		
		String contrasenya = new String(passwordField.getPassword());
		
		btnRegistrarse.setBounds(263, 261, 117, 21);
		contentPane.add(btnRegistrarse);

		//BOTON INICIAR SESIÓN
		btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});
		btnIniciarSesion.setBounds(408, 261, 117, 21);
		contentPane.add(btnIniciarSesion);
		
		
		
		//codigo para menu principal (se abre auto al abrir otra)
		
		//Inicio_sesion dialog = new Inicio_sesion(dialog Menu_principal);
		//dialog.setVisible(true);
		
		switchLanguage(currentLocale);
		
	}
}