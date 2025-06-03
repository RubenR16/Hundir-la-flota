package com.proyecto.hundir_la_flota;

import java.net.*;

public class VerIP {
	public static String miIP(){
		try {
			// Nos conectamos falsamente a una IP externa
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("8.8.8.8", 53)); // DNS de Google


			// Para ver con que IP de mi dispositivo me iba a conectar
			InetAddress localIP = socket.getLocalAddress();
			
			System.out.println("Tu IP local real es: " + localIP.getHostAddress());
			socket.close();
			return localIP.getHostAddress();

		} catch (Exception e) {
			System.out.println("No se pudo obtener la IP real.");
			e.printStackTrace();
		}
		return null;
	}


}

