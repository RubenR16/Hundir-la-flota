package com.proyecto.hundir_la_flota;

import java.io.*;
import java.net.*;

public class ClientePartida {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void conectarServidor(String ip, int puerto) throws IOException {
        socket = new Socket(ip, puerto);
        System.out.println("Conectado a servidor en " + ip + ":" + puerto);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String mensaje = in.readLine();
        System.out.println("Servidor dice: " + mensaje);

        out.println("LISTO_PARA_JUGAR");
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
        socket.close();
    }
}
