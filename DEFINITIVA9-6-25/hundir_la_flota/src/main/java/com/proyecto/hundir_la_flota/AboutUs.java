package com.proyecto.hundir_la_flota;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import messages.Messages;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AboutUs extends JPanel {

    public AboutUs() {
    	setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel(Messages.labels().getString("aboutus.titulo"), SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Texto con JLabel HTML justificado (no seleccionable, sin cursor)
        JLabel textoLabel = new JLabel();
        textoLabel.setText("<html><div style='text-align: justify; font-family: Serif; font-size: 14pt; padding: 10px 40px;'>"
                + Messages.labels().getString("aboutus.texto") + "</div></html>");
        textoLabel.setOpaque(true);
        textoLabel.setBackground(Color.WHITE);
        textoLabel.setBorder(null);

        add(textoLabel, BorderLayout.CENTER);

        // Panel para la imagen
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelImagen.setBorder(new EmptyBorder(10, 0, 20, 0));
        panelImagen.setBackground(Color.WHITE);

        JLabel imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagenLabel.setBorder(new LineBorder(Color.BLACK));

        URL rutaImagen = getClass().getClassLoader().getResource("Images/imagesMc.jpg");

        if (rutaImagen != null) {
            ImageIcon originalIcon = new ImageIcon(rutaImagen);
            int anchoOriginal = originalIcon.getIconWidth();
            int altoOriginal = originalIcon.getIconHeight();

            int anchoMax = 250;
            int altoMax = 250;

            float relacionAncho = (float) anchoMax / anchoOriginal;
            float relacionAlto = (float) altoMax / altoOriginal;
            float escala = Math.min(relacionAncho, relacionAlto);

            int nuevoAncho = (int) (anchoOriginal * escala);
            int nuevoAlto = (int) (altoOriginal * escala);

            Image imagenEscalada = originalIcon.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(imagenEscalada));
        } else {
            imagenLabel.setText(Messages.labels().getString("aboutus.imagenNoEncontrada"));
        }

        panelImagen.add(imagenLabel);
        add(panelImagen, BorderLayout.SOUTH);
    }
}