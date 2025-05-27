package com.proyecto.hundir_la_flota;

public class Barco {
    int tamaño;
    int filaInicial;
    int columnaInicial;
    boolean horizontal;

    public Barco(int tamaño, int fila, int columna, boolean horizontal) {
        this.tamaño = tamaño;
        this.filaInicial = fila;
        this.columnaInicial = columna;
        this.horizontal = horizontal;
    }
}