package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TableroEnemigo extends JPanel {
    private final int filas = 10, columnas = 10;
    private final CasillaEnemiga[][] casillas = new CasillaEnemiga[filas][columnas];
    private PartidaFrame partidaFrame; // Referencia al frame principal
    
    public TableroEnemigo(PartidaFrame partidaFrame) {
        this.partidaFrame = partidaFrame;
        setLayout(new GridLayout(filas, columnas));
        
        // Crear cada casilla del tablero enemigo
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                CasillaEnemiga casilla = new CasillaEnemiga(i, j);
                casilla.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (partidaFrame.esMiTurno() && !casilla.yaDisparada()) {
                            partidaFrame.realizarDisparo(casilla.getFila(), casilla.getColumna());
                        }
                    }
                    
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (partidaFrame.esMiTurno() && !casilla.yaDisparada()) {
                            casilla.setBackground(Color.YELLOW); // Resaltar casilla
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!casilla.yaDisparada()) {
                            casilla.setBackground(Color.LIGHT_GRAY); // Color por defecto
                        }
                    }
                });
                casillas[i][j] = casilla;
                add(casilla);
            }
        }
        setPreferredSize(new Dimension(369, 297));
    }
    
    // Marcar resultado de disparo
    public void marcarDisparo(int fila, int columna, boolean acierto) {
        casillas[fila][columna].marcarDisparo(acierto);
    }
    
    // Marcar barco hundido (color especial)
    public void marcarBarcoHundido(int fila, int columna, int tamaño, boolean horizontal) {
        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = columna + (horizontal ? i : 0);
            if (f >= 0 && f < filas && c >= 0 && c < columnas) {
                casillas[f][c].marcarHundido();
            }
        }
    }
    
    // Clase interna para las casillas del tablero enemigo
    private class CasillaEnemiga extends JPanel {
        private final int fila, columna;
        private boolean disparada = false;
        
        public CasillaEnemiga(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
            setPreferredSize(new Dimension(30, 30));
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        
        public int getFila() { return fila; }
        public int getColumna() { return columna; }
        public boolean yaDisparada() { return disparada; }
        
        public void marcarDisparo(boolean acierto) {
            disparada = true;
            setBackground(acierto ? Color.RED : Color.BLUE);
            // Agregar una X o símbolo visual
            removeAll();
            JLabel label = new JLabel(acierto ? "X" : "•", JLabel.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, acierto ? 18 : 16));
            add(label);
            revalidate();
            repaint();
        }
        
        public void marcarHundido() {
            disparada = true;
            setBackground(Color.DARK_GRAY);
            // Agregar símbolo especial para hundido
            removeAll();
            JLabel label = new JLabel("†", JLabel.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 20));
            add(label);
            revalidate();
            repaint();
        }
    }
}