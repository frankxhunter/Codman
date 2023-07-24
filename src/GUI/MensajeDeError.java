package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class MensajeDeError extends JDialog {

	private JPanel contentPane;
	private JButton btnNewButton_1_1_1;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public MensajeDeError(String  message) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 395, 200);
		setUndecorated(true);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 36,39));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBorder(BorderFactory.createLineBorder(new Color(44, 133, 121), 2));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MiBoton btnAceptar = new MiBoton();
		btnAceptar.setFont(new Font("Arial", Font.BOLD, 16));
		btnAceptar.setText("Aceptar");
		btnAceptar.setRadio(30);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btnAceptar.isEnabled())
				btnAceptar.setBackground(new Color(66, 211, 192));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(btnAceptar.isEnabled())
				btnAceptar.setBackground(new Color(44,133,121));
			}


			@Override
			public void mousePressed(MouseEvent e) {
				if(btnAceptar.isEnabled())
				btnAceptar.setBackground(new Color(44,133,121));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnAceptar.isEnabled())
				btnAceptar.setBackground(new Color(66, 211, 192));
			}
		});
		btnAceptar.setFocusable(false);
		btnAceptar.setBorder(null);
		btnAceptar.setForeground(new Color(255, 255, 255));
		btnAceptar.setBackground(new Color(44, 133, 121));
		btnAceptar.setBounds(276, 153, 109, 37);
		contentPane.add(btnAceptar);
		
		
		
		
		
		JLabel lblMensajeError = new JLabel(message);
		lblMensajeError.setFont(new Font("Arial", Font.BOLD, 13));
		lblMensajeError.setBackground(new Color(44, 133, 121));
		lblMensajeError.setForeground(new Color(255, 255, 255));
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setBounds(42, 82, 343, 31);
		contentPane.add(lblMensajeError);
		
		JLabel lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIcon(MensajeDeError.class.getResource("/ICONS/icons8_Error_48.png")));
		
		lblIcon.setBounds(10, 82, 48, 37);
		contentPane.add(lblIcon);
		contentPane.add(getBtnNewButton_1_1_1());
		
		JLabel lblNewLabel = new JLabel("Error");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setIcon(new ImageIcon(MensajeDeError.class.getResource("/ICONS/icons8_Error_32.png")));
		lblNewLabel.setBounds(10, 10, 295, 31);
		contentPane.add(lblNewLabel);
	}
	private JButton getBtnNewButton_1_1_1() {
		if (btnNewButton_1_1_1 == null) {
			btnNewButton_1_1_1 = new JButton("");
			btnNewButton_1_1_1.setRolloverIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_DeleteSelected_32.png")));
			btnNewButton_1_1_1.setSelectedIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_Delete_32.png")));
			btnNewButton_1_1_1.setBackground(new Color(32,36,39));
			btnNewButton_1_1_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnNewButton_1_1_1.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_Delete_32.png")));
			btnNewButton_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
			btnNewButton_1_1_1.setForeground(Color.WHITE);
			btnNewButton_1_1_1.setFont(new Font("Arial", Font.BOLD, 20));
			btnNewButton_1_1_1.setFocusable(false);
			btnNewButton_1_1_1.setBorderPainted(false);
			btnNewButton_1_1_1.setBounds(361, 10, 24, 24);
		}
		return btnNewButton_1_1_1;
	}
}
