package com.proyecto.hundir_la_flota;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registrarse extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCorreo;
	private JTextField textFieldContraseña;
	private JTextField textField;


	public Registrarse(JFrame parent) {
		super(parent, "Registrarse", true); // true = modal
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 830, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldCorreo = new JTextField();
		textFieldCorreo.setToolTipText("");
		textFieldCorreo.setBounds(334, 129, 153, 19);
		contentPane.add(textFieldCorreo);
		textFieldCorreo.setColumns(10);

		textFieldContraseña = new JTextField();
		textFieldContraseña.setBounds(334, 194, 153, 19);
		contentPane.add(textFieldContraseña);
		textFieldContraseña.setColumns(10);

		JLabel lblNewLabelUser = new JLabel("Usuario:");
		lblNewLabelUser.setBounds(334, 106, 153, 13);
		contentPane.add(lblNewLabelUser);

		JLabel lblNewLabel = new JLabel("Contraseña:");
		lblNewLabel.setBounds(334, 171, 153, 13);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// lógica de confirmación
			}
		});
		btnNewButton.setBounds(443, 304, 117, 21);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Confirmar contraseña:");
		lblNewLabel_1.setBounds(334, 223, 153, 13);
		contentPane.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(334, 246, 153, 19);
		contentPane.add(textField);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose()); // cerrar la ventana
		btnCancelar.setBounds(259, 304, 117, 21);
		contentPane.add(btnCancelar);
	}
}
