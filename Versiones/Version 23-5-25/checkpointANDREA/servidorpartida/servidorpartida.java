package com.proyecto.hundir_la_flota;

import java.io.*;
import java.net.*;

public class ServidorPartida {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader in;
	private PrintWriter out;
	private int disparosRecibidos = 0;


	// Simulamos un barco en la posición 3,3
	private final int BARCO_FILA = 3;
	private final int BARCO_COL = 3;

	public void iniciarServidor(int puerto) throws IOException {
		serverSocket = new ServerSocket(puerto);
		System.out.println("Servidor esperando conexión en el puerto " + puerto + "...");
		clientSocket = serverSocket.accept();
		System.out.println("Jugador conectado desde " + clientSocket.getInetAddress());

		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out = new PrintWriter(clientSocket.getOutputStream(), true);

		out.println("BIENVENIDO_JUGADOR");

		new Thread(() -> {
			try {
				String linea;
				while ((linea = in.readLine()) != null) {
					System.out.println("Cliente dice: " + linea);
				
					
					
					if (linea.startsWith("DISPARO")) {
						String[] partes = linea.split(" ");
						int fila = Integer.parseInt(partes[1]);
						int col = Integer.parseInt(partes[2]);
						
						disparosRecibidos++;

						// Aquí iría la lógica real de comprobar barcos
						if (fila == BARCO_FILA && col == BARCO_COL) {
							out.println("TOCADO " + fila + " " + col);
						} else {
							out.println("AGUA " + fila + " " + col);
						}
						
						if (disparosRecibidos >= 3) {
					        out.println("FIN_TURNO");
					        disparosRecibidos = 0; // Reiniciar para el próximo turno
					    }
						
						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void cerrar() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}
}
