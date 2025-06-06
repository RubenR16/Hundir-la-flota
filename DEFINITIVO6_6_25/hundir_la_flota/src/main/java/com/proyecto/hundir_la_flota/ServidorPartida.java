package com.proyecto.hundir_la_flota;

import java.io.*;
import java.net.*;

public class ServidorPartida {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public void iniciarServidor(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        System.out.println("Servidor esperando conexión en el puerto " + puerto + "...");
        clientSocket = serverSocket.accept();
        System.out.println("Jugador conectado desde " + clientSocket.getInetAddress());

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Mensaje de bienvenida
        out.println("BIENVENIDO_JUGADOR");
        String respuesta = in.readLine();
        System.out.println("Cliente dice: " + respuesta);
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void cerrar() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}