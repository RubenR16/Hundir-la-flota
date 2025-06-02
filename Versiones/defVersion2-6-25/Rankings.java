package com.proyecto.hundir_la_flota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import messages.Messages;

public class Rankings extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private Connection connection;
    private JLabel lblRanking;
    private DefaultTableModel model;

    // Clase interna para almacenar datos de ranking
    public static class UsuarioRanking {
        public String nombreUsuario;
        public int puntuacion;

        public UsuarioRanking(String nombreUsuario, int puntuacion) {
            this.nombreUsuario = nombreUsuario;
            this.puntuacion = puntuacion;
        }
    }

    public Rankings(Connection connection, Locale locale) {
        this.connection = connection;

        setTitle(Messages.labels().getString("label.TituloRanking"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 680, 390);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblRanking = new JLabel();
        lblRanking.setBounds(300, 10, 200, 13);
        contentPane.add(lblRanking);

        model = new DefaultTableModel(null, new String[]{"", "", ""});
        table = new JTable(model);
        table.setEnabled(false); // Tabla no editable
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(150, 50, 360, 184);
        contentPane.add(scrollPane);

        switchLanguage(locale); //Traducción

        llenarTablaRanking();
    }

    private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        lblRanking.setText(Messages.labels().getString("label.lblRanking"));

        model.setColumnIdentifiers(new String[]{
            Messages.labels().getString("label.lblPosicion"),
            Messages.labels().getString("label.lblUsuario"),
            Messages.labels().getString("label.lblPuntuacion")
        });
    }

    private void llenarTablaRanking() {
        List<UsuarioRanking> top10 = obtenerTop10Ranking();
        String[] posiciones = {
            "1ST", "2ND", "3RD", "4TH", "5TH",
            "6TH", "7TH", "8TH", "9TH", "10TH"
        };

        for (int i = 0; i < top10.size(); i++) {
            UsuarioRanking usuario = top10.get(i);
            model.addRow(new Object[]{
                posiciones[i],
                usuario.nombreUsuario,
                usuario.puntuacion
            });
        }
    }

    public List<UsuarioRanking> obtenerTop10Ranking() {
        List<UsuarioRanking> top10 = new ArrayList<>();
        String sql = "SELECT nombre_usuario, puntos_totales FROM usuarios WHERE baneado = 0 ORDER BY puntos_totales DESC LIMIT 10";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                top10.add(new UsuarioRanking(
                    rs.getString("nombre_usuario"),
                    rs.getInt("puntos_totales")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return top10;
    }
}