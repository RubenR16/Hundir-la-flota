package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Estadisticas_personales extends JFrame {

	private JPanel contentPane;
	private String nombreUsuario;
	private static Connection connection;
	private static PreparedStatement stmtNPJ;
	private static PreparedStatement stmtNBH;
	private static PreparedStatement stmtNPT;
	private static PreparedStatement stmtCAG;
	
	

	
	public Estadisticas_personales(String nombreUsuario) {
		
		this.nombreUsuario = nombreUsuario;
		ConexionMySQL c = new ConexionMySQL();
		connection = c.getCon();
		
		String numPartJug="";
		String numBarcHun="";
		String numPuntTot="";
		String casAguaGolp="";
		
		try {
			stmtNPJ = connection.prepareStatement("select partidas_jugadas from usuarios where nombre_usuario = ?");
			stmtNPJ.setString(1,nombreUsuario);
			ResultSet rs = stmtNPJ.executeQuery();
			if(rs.next()) {
				numPartJug=rs.getString("partidas_jugadas");
			}
			
			stmtNBH = connection.prepareStatement("select barcos_hundidos from usuarios where nombre_usuario = ?");
			stmtNBH.setString(1,nombreUsuario);
			rs = stmtNBH.executeQuery();
			if(rs.next()) {
				numBarcHun=rs.getString("barcos_hundidos");
			}
			
			stmtNPT = connection.prepareStatement("select puntos_totales from usuarios where nombre_usuario = ?");
			stmtNPT.setString(1,nombreUsuario);
			rs = stmtNPT.executeQuery();
			if(rs.next()) {
				numPuntTot=rs.getString("puntos_totales");
			}
			
			stmtCAG = connection.prepareStatement("select casillas_agua_golpeadas from usuarios where nombre_usuario = ?");
			stmtCAG.setString(1,nombreUsuario);
			rs = stmtCAG.executeQuery();
			if(rs.next()) {
				casAguaGolp=rs.getString("casillas_agua_golpeadas");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setTitle("Estadisticas Personales");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPartidasJugadas = new JLabel("Partidas jugadas");
		lblPartidasJugadas.setBounds(113, 42, 101, 17);
		contentPane.add(lblPartidasJugadas);
		
		JLabel lblBarcosHundidos = new JLabel("Barcos hundidos");
		lblBarcosHundidos.setBounds(113, 67, 99, 17);
		contentPane.add(lblBarcosHundidos);
		
		JLabel lblPuntos = new JLabel("Puntos totales");
		lblPuntos.setBounds(113, 96, 86, 17);
		contentPane.add(lblPuntos);
		
		JLabel lblCasillasAgua = new JLabel("Casillas de agua golpeadas");
		lblCasillasAgua.setBounds(113, 121, 162, 17);
		contentPane.add(lblCasillasAgua);
		
		JLabel lblNumPartidas = new JLabel(numPartJug);
		lblNumPartidas.setBounds(291, 42, 60, 17);
		contentPane.add(lblNumPartidas);
		
		JLabel lblNumBarcos = new JLabel(numBarcHun);
		lblNumBarcos.setBounds(291, 67, 60, 17);
		contentPane.add(lblNumBarcos);
		
		JLabel lblNumPuntos = new JLabel(numPuntTot);
		lblNumPuntos.setBounds(291, 96, 60, 17);
		contentPane.add(lblNumPuntos);
		
		JLabel lblNumCasillasAgua = new JLabel(casAguaGolp);
		lblNumCasillasAgua.setBounds(291, 121, 60, 17);
		contentPane.add(lblNumCasillasAgua);
	
		JLabel lblUsuario = new JLabel(nombreUsuario);
		lblUsuario.setBounds(189, 13, 148, 17);
		contentPane.add(lblUsuario);
		
		
		
	}

}