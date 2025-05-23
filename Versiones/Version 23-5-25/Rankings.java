package com.proyecto.hundir_la_flota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Rankings extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JLabel lblPrimero;
    private JLabel lblSegundo;
    private JLabel lblTercero;
    private JLabel lblCuarto;
    private JLabel lblQuinto;

    private Connection connection;

    // Clase interna para almacenar datos de ranking
    public static class UsuarioRanking {
        public String nombreUsuario;
        public int puntuacion;

        public UsuarioRanking(String nombreUsuario, int puntuacion) {
            this.nombreUsuario = nombreUsuario;
            this.puntuacion = puntuacion;
        }
    }

    public Rankings(Connection connection) {
        this.connection = connection;

        setTitle("Ranking");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 680, 390);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblRanking = new JLabel("RANKING");
        lblRanking.setBounds(312, 10, 88, 13);
        contentPane.add(lblRanking);

        JPanel panel = new JPanel();
        panel.setBounds(110, 75, 376, 197);
        contentPane.add(panel);
        panel.setLayout(null);

        lblPrimero = new JLabel();
        lblPrimero.setBounds(158, 0, 200, 41);
        panel.add(lblPrimero);

        lblSegundo = new JLabel();
        lblSegundo.setBounds(158, 40, 200, 34);
        panel.add(lblSegundo);

        lblTercero = new JLabel();
        lblTercero.setBounds(158, 75, 200, 34);
        panel.add(lblTercero);

        lblCuarto = new JLabel();
        lblCuarto.setBounds(158, 107, 200, 34);
        panel.add(lblCuarto);

        lblQuinto = new JLabel();
        lblQuinto.setBounds(158, 151, 200, 34);
        panel.add(lblQuinto);

        mostrarRanking();
    }

    private void mostrarRanking() {
        List<UsuarioRanking> top5 = obtenerTop5Ranking();

        if (top5.size() > 0) lblPrimero.setText("1ST: " + top5.get(0).puntuacion + " " + top5.get(0).nombreUsuario);
        if (top5.size() > 1) lblSegundo.setText("2ND: " + top5.get(1).puntuacion + " " + top5.get(1).nombreUsuario);
        if (top5.size() > 2) lblTercero.setText("3RD: " + top5.get(2).puntuacion + " " + top5.get(2).nombreUsuario);
        if (top5.size() > 3) lblCuarto.setText("4TH: " + top5.get(3).puntuacion + " " + top5.get(3).nombreUsuario);
        if (top5.size() > 4) lblQuinto.setText("5TH: " + top5.get(4).puntuacion + " " + top5.get(4).nombreUsuario);
    }

    public List<UsuarioRanking> obtenerTop5Ranking() {
        List<UsuarioRanking> top5 = new ArrayList<>();
        String sql = "SELECT nombre_usuario, puntuacion FROM ranking ORDER BY puntuacion DESC LIMIT 5";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                top5.add(new UsuarioRanking(rs.getString("nombre_usuario"), rs.getInt("puntuacion")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top5;
    }
}
