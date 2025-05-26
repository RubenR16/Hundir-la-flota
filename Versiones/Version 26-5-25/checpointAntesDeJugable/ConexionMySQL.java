package com.proyecto.hundir_la_flota;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

	public class ConexionMySQL {
		
	private static Connection con;
	
	private final String URL= "jdbc:mysql://bddbarquitos.ccrnujijmw1q.us-east-1.rds.amazonaws.com:3306/hundir_la_flota";
	private final String USER="root";
	private final String PASSWORD="barquitos";
	
	public ConexionMySQL(){
		
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					con=DriverManager.getConnection(URL,USER,PASSWORD);
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				
			
}
	
	public Connection getCon() {
		return con;
	}
	
	
}
