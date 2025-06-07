package com.proyecto.hundir_la_flota;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import messages.Messages;

	public class ConexionMySQL {
		
	private static Connection con;
	
	private final String URL= "jdbc:mysql://bddbarquitos.ccrnujijmw1q.us-east-1.rds.amazonaws.com:3306/hundir_la_flota";
	private final String USER="root";
	private final String PASSWORD="barquitos";
	
	public ConexionMySQL(){
		
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con=DriverManager.getConnection(URL,USER,PASSWORD);
					System.out.println("Conexión exitosa a la base de datos.");
					
				} catch (CommunicationsException ce) {
					 Object[] options = {
						        Messages.labels().getString("button.aceptar")
						    };
						    JOptionPane.showOptionDialog(
						        null,
						        Messages.errors().getString("error.dbConnection") + "\n\n",
						        Messages.errors().getString("error.dbCommTitle"),
						        JOptionPane.DEFAULT_OPTION,
						        JOptionPane.ERROR_MESSAGE,
						        null,
						        options,
						        options[0]
						    );
		        } catch (ClassNotFoundException e) {
		        	
		        	System.out.println(e);

					e.printStackTrace();
				} catch (SQLException e) {

					System.out.println(e);
				}
		    }
				
			

	
	public Connection getCon() {
		return con;
	}
	
	
}