package com.proyecto.hundir_la_flota;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContrasenyaCifrada {
    public static String hashContrasenya(String contrasenya) {
        try {
        	// Crea un objeto con el algoritmo puesto
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Convierte la contraseña en bytes y calcula el hash con ella. Devuelve un array de bytes (la contra más segura)
            byte[] hashBytes = md.digest(contrasenya.getBytes());

            // Para ir pegando los bytes ya cifrados, uno por uno.
            StringBuilder sb = new StringBuilder();
           
            // Convierte cada byte del hash en un texto hexadecimal
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            // Devuelve la contraseña, cifrada
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Se ha producido un error, intentalo de nuevo", e);
        }
    }
}
