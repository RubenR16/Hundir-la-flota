package proyecto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Estadisticas_personales extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Estadisticas_personales frame = new Estadisticas_personales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Estadisticas_personales() {
		setTitle("Estadisticas Personales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPartidasJugadas = new JLabel("Partidas jugadas");
		lblPartidasJugadas.setBounds(113, 42, 101, 17);
		contentPane.add(lblPartidasJugadas);
		
		JLabel lblBarcosHundidos = new JLabel("Barcos hundidos");
		lblBarcosHundidos.setBounds(113, 67, 99, 17);
		contentPane.add(lblBarcosHundidos);
		
		JLabel lblNewLabel = new JLabel("Puntos totales");
		lblNewLabel.setBounds(113, 96, 86, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Casillas de agua golpeadas");
		lblNewLabel_1.setBounds(113, 121, 162, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("1");
		label.setBounds(291, 42, 60, 17);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("4");
		label_1.setBounds(291, 67, 60, 17);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("1300");
		label_2.setBounds(291, 96, 60, 17);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("15");
		label_3.setBounds(291, 121, 60, 17);
		contentPane.add(label_3);
		
		JLabel lblNewLabel_2 = new JLabel("Usuario123");
		lblNewLabel_2.setBounds(189, 13, 148, 17);
		contentPane.add(lblNewLabel_2);
	}

}
