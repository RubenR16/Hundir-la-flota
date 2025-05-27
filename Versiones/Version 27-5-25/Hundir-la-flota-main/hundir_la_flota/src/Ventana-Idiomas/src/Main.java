
package com.ejemplo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Formulario().setVisible(true));
    }
}

class Formulario extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JButton submitButton;
    private JMenuBar menuBar;

    public Formulario() {
        setTitle("Formulario");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        nameLabel = new JLabel(Messages.labels().getString("label.name"));
        emailLabel = new JLabel(Messages.labels().getString("label.email"));
        nameField = new JTextField(15);
        emailField = new JTextField(15);

        submitButton = new JButton(Messages.labels().getString("button.submit"));
        submitButton.addActionListener(this::onSubmit);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(new JLabel());
        panel.add(submitButton);

        setJMenuBar(createMenuBar());

        add(panel);
    }

    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        JMenu languageMenu = new JMenu("Language");

        JMenuItem spanish = new JMenuItem("Español");
        spanish.addActionListener(e -> switchLanguage(new Locale("es", "ES")));

        JMenuItem english = new JMenuItem("English");
        english.addActionListener(e -> switchLanguage(new Locale("en", "US")));

        languageMenu.add(spanish);
        languageMenu.add(english);
        menuBar.add(languageMenu);
        return menuBar;
    }

    private void switchLanguage(Locale locale) {
        Messages.loadLocale(locale);
        nameLabel.setText(Messages.labels().getString("label.name"));
        emailLabel.setText(Messages.labels().getString("label.email"));
        submitButton.setText(Messages.labels().getString("button.submit"));
    }

    private void onSubmit(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty()) {
            showMessage(Messages.errors().getString("error.empty_name"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (email.isEmpty()) {
            showMessage(Messages.errors().getString("error.empty_email"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@")) {
            showMessage(Messages.warnings().getString("warning.email_format"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        showMessage(Messages.info().getString("info.submission_success"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void showMessage(String message, int type) {
        JOptionPane.showMessageDialog(this, message, "Mensaje", type);
    }
}