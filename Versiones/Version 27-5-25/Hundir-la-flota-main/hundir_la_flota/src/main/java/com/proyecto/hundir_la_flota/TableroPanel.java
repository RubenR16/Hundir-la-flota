package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TableroPanel extends JPanel {
    private final int filas = 10, columnas = 10;
    private final Casilla[][] casillas = new Casilla[filas][columnas];

    private java.util.List<Barco> barcosColocados = new ArrayList<>();
    private int barcoSeleccionado = -1;
    private boolean horizontal = true;
    private int[] barcosPendientes = {5, 4, 3, 3, 2};
    private ControlPanel controlPanel;
    private JuegoFrame juegoFrame;

    // Colores asociados a cada tipo de barco
    private final Color[] coloresBarcos = {
        new Color(235, 152, 78),
        new Color(100, 255, 100),
        new Color(100, 100, 255),
        new Color(255, 255, 100),
        new Color(255, 150, 255)
    };

    public TableroPanel() {
        setLayout(new GridLayout(filas, columnas));

        // Crear cada casilla del tablero y agregar listeners de ratón
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = new Casilla(i, j);
                casilla.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (barcoSeleccionado >= 0) { // Solo durante colocación
                            vistaPrevia(casilla.getFila(), casilla.getColumna());
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (barcoSeleccionado >= 0) {
                            limpiarVistaPrevia();
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (barcoSeleccionado >= 0) { // Solo durante colocación
                            colocarBarco(casilla.getFila(), casilla.getColumna());
                        }
                    }
                });
                casillas[i][j] = casilla;
                add(casilla);
            }
        }
        setPreferredSize(new Dimension(400, 400));
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        controlPanel.bloquearTodosMenos(0);
        barcoSeleccionado = 0;
    }

    public void setJuegoFrame(JuegoFrame juegoFrame) {
        this.juegoFrame = juegoFrame;
    }

    public void seleccionarBarco(int index) {
        if (index == barcoSeleccionado) {
            barcoSeleccionado = index;
        }
    }

    public void cambiarOrientacion() {
        horizontal = !horizontal;
    }

    public int getBarcosColocados() {
        return barcosColocados.size();
    }

    // Método para obtener la lista de barcos (necesario para PartidaFrame)
    public java.util.List<Barco> getListaBarcos() {
        return new ArrayList<>(barcosColocados);
    }

    // Método para marcar disparos recibidos durante la partida
    public void marcarDisparoRecibido(int fila, int columna, boolean acierto) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            Casilla casilla = casillas[fila][columna];
            if (acierto) {
                casilla.marcarTocado(); // Rojo para acierto
            } else {
                casilla.marcarAgua(); // Azul para agua
            }
        }
    }

    // Deshabilitar interacción durante la partida
    public void deshabilitarColocacion() {
        barcoSeleccionado = -1;
        // Remover todos los listeners de mouse
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                MouseListener[] listeners = casillas[i][j].getMouseListeners();
                for (MouseListener listener : listeners) {
                    casillas[i][j].removeMouseListener(listener);
                }
            }
        }
    }

    private void vistaPrevia(int fila, int columna) {
        limpiarVistaPrevia();
        if (barcoSeleccionado >= barcosPendientes.length) return;

        int tamaño = barcosPendientes[barcoSeleccionado];

        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = columna + (horizontal ? i : 0);
            if (f >= filas || c >= columnas) return;
            casillas[f][c].setPreview(true);
        }
    }

    private void limpiarVistaPrevia() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j].setPreview(false);
            }
        }
    }

    private void colocarBarco(int fila, int columna) {
        if (barcoSeleccionado >= barcosPendientes.length) return;
        int tamaño = barcosPendientes[barcoSeleccionado];

        ArrayList<Casilla> posibles = new ArrayList<>();
        for (int i = 0; i < tamaño; i++) {
            int f = fila + (horizontal ? 0 : i);
            int c = columna + (horizontal ? i : 0);
            if (f >= filas || c >= columnas || casillas[f][c].tieneBarco()) return;
            posibles.add(casillas[f][c]);
        }

        for (Casilla casilla : posibles) {
            casilla.colocarBarco(coloresBarcos[barcoSeleccionado]);
        }

        barcosColocados.add(new Barco(tamaño, fila, columna, horizontal));
        barcoSeleccionado++;
        limpiarVistaPrevia();

        if (controlPanel != null) {
            controlPanel.bloquearTodosMenos(barcoSeleccionado);
        }

        if (barcoSeleccionado >= barcosPendientes.length) {
            if (juegoFrame != null) {
                juegoFrame.todosLosBarcosColocados();
            }
            deshabilitarColocacion();
        }
    }
}
