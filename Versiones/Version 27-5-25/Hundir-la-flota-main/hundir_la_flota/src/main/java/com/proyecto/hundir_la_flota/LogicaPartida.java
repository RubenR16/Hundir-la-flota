package com.proyecto.hundir_la_flota;

import java.util.ArrayList;
import java.util.List;

public class LogicaPartida {
    private final int filas = 10, columnas = 10;
    private boolean[][] tableroPropio = new boolean[filas][columnas];
    private boolean[][] disparosRecibidos = new boolean[filas][columnas];
    private List<Barco> barcosPropio = new ArrayList<>();
    
    // Constructor que recibe los barcos colocados
    public LogicaPartida(List<Barco> barcos) {
        this.barcosPropio = new ArrayList<>(barcos);
        inicializarTablero();
    }
    
    // Inicializar tablero con posiciones de barcos
    private void inicializarTablero() {
        for (Barco barco : barcosPropio) {
            for (int i = 0; i < barco.tamaño; i++) {
                int fila = barco.filaInicial + (barco.horizontal ? 0 : i);
                int columna = barco.columnaInicial + (barco.horizontal ? i : 0);
                tableroPropio[fila][columna] = true;
            }
        }
    }
    
    // Procesar disparo enemigo en nuestro tablero
    public boolean recibirDisparo(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false; // Coordenadas inválidas
        }
        
        disparosRecibidos[fila][columna] = true;
        return tableroPropio[fila][columna]; // true = acierto, false = agua
    }
    
    // Verificar si un barco específico está hundido
    public boolean verificarBarcoHundido(Barco barco) {
        for (int i = 0; i < barco.tamaño; i++) {
            int fila = barco.filaInicial + (barco.horizontal ? 0 : i);
            int columna = barco.columnaInicial + (barco.horizontal ? i : 0);
            if (!disparosRecibidos[fila][columna]) {
                return false; // Al menos una parte del barco no ha sido tocada
            }
        }
        return true; // Todo el barco ha sido tocado
    }
    
    // Verificar si todos los barcos están hundidos (fin del juego)
    public boolean todosLosBarcosHundidos() {
        for (Barco barco : barcosPropio) {
            if (!verificarBarcoHundido(barco)) {
                return false;
            }
        }
        return true;
    }
    
    // Obtener información del barco que fue hundido (si existe)
    public Barco obtenerBarcoEn(int fila, int columna) {
        for (Barco barco : barcosPropio) {
            for (int i = 0; i < barco.tamaño; i++) {
                int f = barco.filaInicial + (barco.horizontal ? 0 : i);
                int c = barco.columnaInicial + (barco.horizontal ? i : 0);
                if (f == fila && c == columna) {
                    return barco;
                }
            }
        }
        return null;
    }
    
    // Getters
    public boolean[][] getTableroPropio() { return tableroPropio; }
    public boolean[][] getDisparosRecibidos() { return disparosRecibidos; }
    public List<Barco> getBarcos() { return barcosPropio; }
}