package GUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicSpinnerUI;

import CodeMan_Compress.CodeMan;
import ourTools.Convert;
import ourTools.Validation;

public class Secundaria_Comprimir extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Secundaria_Comprimir frame = new Secundaria_Comprimir( new File("Escritorio\\ParaBing.pdf"), null);
					frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	BasicSpinnerUI BSUI = new BasicSpinnerUI() {

		protected Component createNextButton() {
			JButton button = (JButton) super.createNextButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}		
		protected Component createPreviousButton() {
			JButton button = (JButton) super.createPreviousButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}
		protected JComponent createEditor() {
			JSpinner.NumberEditor editor = (JSpinner.NumberEditor) super.createEditor();
			JFormattedTextField textField = editor.getTextField(); 
			textField.setBackground(new Color(54,54,54));
			textField.setForeground(Color.WHITE);	        
			return editor;
		}
	};

	BasicSpinnerUI BSUI2 = new BasicSpinnerUI() {

		protected Component createNextButton() {
			JButton button = (JButton) super.createNextButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}		
		protected Component createPreviousButton() {
			JButton button = (JButton) super.createPreviousButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}
		protected JComponent createEditor() {
			JSpinner.NumberEditor editor = (JSpinner.NumberEditor) super.createEditor();
			JFormattedTextField textField = editor.getTextField(); 
			textField.setBackground(new Color(54,54,54));
			textField.setForeground(Color.WHITE);	        
			return editor;
		}
	};

	BasicSpinnerUI BSUI3 = new BasicSpinnerUI() {

		protected Component createNextButton() {
			JButton button = (JButton) super.createNextButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}		
		protected Component createPreviousButton() {
			JButton button = (JButton) super.createPreviousButton();
			button.setBackground(new Color(54,54,54));
			return  button;
		}
		protected JComponent createEditor() {
			JSpinner.NumberEditor editor = (JSpinner.NumberEditor) super.createEditor();
			JFormattedTextField textField = editor.getTextField(); 
			textField.setBackground(new Color(54,54,54));
			textField.setForeground(Color.WHITE);	        
			return editor;
		}
	};
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private int posX;
	private int posY;
	private JPanel panel_1;
	private JSpinner spinnerVentana;
	private JSpinner spinnerTamBloque;
	private JSpinner spinnerCoincidencia;
	private JLabel lblNewLabel;
	private File fileProcesar;
	private JLabel lblRutaMarcada;
	private JTextField textNombre;
	private JLabel lblProceso;
	private JFrame framePrincipal;


	public Secundaria_Comprimir( File file, JFrame framePrincipal) {
		this.framePrincipal= framePrincipal;
		this.fileProcesar= file;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 713, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		setUndecorated(true);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(32,36,39));
		panel.setBorder(BorderFactory.createLineBorder(new Color(44, 133, 121), 2));
		panel.setBounds(0, 0, 713, 511);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX= e.getX();
				posY= e.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				getThis().setLocation(e.getXOnScreen()-posX, e.getYOnScreen()-posY);
			}
		});
		contentPane.add(panel);
		panel.setLayout(null);

		MiBoton btnAceptar = new MiBoton();
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblProceso.setText("Procesando....");
					File entrada= CodeMan.compress(fileProcesar, lblRutaMarcada.getText(),textNombre.getText(), Integer.parseInt(spinnerTamBloque.getValue().toString()),
							Integer.parseInt(spinnerVentana.getValue().toString()),Integer.parseInt(spinnerCoincidencia.getValue().toString()));


					lblProceso.setText("Archivo Comprimido");
					lblProceso.setIcon(new ImageIcon(Secundaria_Comprimir.class.getResource("/ICONS/icons8_Checkmark_32.png")));

				}
				catch (StreamCorruptedException x) {
					
					MensajeDeError error = new MensajeDeError("Archivo corrputo o dañado");
					error.setModal(true);
					error.setResizable(false);
					error.setLocationRelativeTo(getThis());
					error.setVisible(true);
					System.out.println("Archivo corrupto o dañado");

					lblProceso.setText("Error");
				}
				catch (IOException x) {
					String[] splitMessage = new String[2];
					splitMessage = x.getMessage().split("\\(");
					String message = splitMessage[1].replace(")", "");
					MensajeDeError error = new MensajeDeError(message);
					error.setModal(true);
					error.setResizable(false);
					error.setLocationRelativeTo(getThis());
					error.setVisible(true);
					System.out.println("Archivo corrupto o dañado");

					lblProceso.setText("Error");
				}

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
		btnAceptar.setForeground(new Color(255, 255, 255));
		btnAceptar.setFont(new Font("Arial", Font.PLAIN, 18));
		btnAceptar.setRadio(30);
		btnAceptar.setLocation(382, 467);
		btnAceptar.setSize(310, 34);
		btnAceptar.setText("Comprimir");

		panel.add(btnAceptar);

		PanelRound pr = new PanelRound();
		pr.setBounds(26, 336, 593, 48);
		pr.setBackground(new Color(54,54,54));
		pr.setRoundBottomLeft(50);
		pr.setRoundBottomRight(50);
		pr.setRoundTopLeft(50);
		pr.setRoundTopRight(50);	
		panel.add(pr);
		pr.setLayout(null);

		lblRutaMarcada = new JLabel("");
		lblRutaMarcada.setText(fileProcesar.getParent());
		lblRutaMarcada.setForeground(Color.WHITE);
		lblRutaMarcada.setFont(new Font("Arial", Font.PLAIN, 15));
		lblRutaMarcada.setBounds(10, 0, 584, 45);
		pr.add(lblRutaMarcada);

		MiBoton btnFind = new MiBoton();
		btnFind.setToolTipText("Para seleccionar la ruta donde se va a guardar el archivo");
		btnFind.setIcon(new ImageIcon(Secundaria_Comprimir.class.getResource("/ICONS/buscar_32.png")));
		btnFind.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btnFind.isEnabled())
					btnFind.setBackground(new Color(66, 211, 192));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(btnFind.isEnabled())
					btnFind.setBackground(new Color(44,133,121));
			}


			@Override
			public void mousePressed(MouseEvent e) {
				if(btnFind.isEnabled())
					btnFind.setBackground(new Color(44,133,121));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(btnFind.isEnabled())
					btnFind.setBackground(new Color(66, 211, 192));
			}
		});
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fc = new JFileChooser(fileProcesar.getParent());

				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int seleccion = fc.showOpenDialog(rootPane);
				if(seleccion == JFileChooser.APPROVE_OPTION){
					File fichero = fc.getSelectedFile();
					lblRutaMarcada.setText(fichero.getAbsolutePath());
				}
			}
		}
				);
		btnFind.setFocusable(false);
		btnFind.setRadio(50);
		btnFind.setBounds(641, 336, 51, 48);
		panel.add(btnFind);

		MiBoton btnCancelar = new MiBoton();
		btnCancelar.setText("Cancelar");
		btnCancelar.setRadio(25);
		btnCancelar.setForeground(Color.WHITE);

		btnCancelar.setBounds(26, 467, 320, 34);
		btnCancelar.setBorder(null);
		btnCancelar.setBackground(new Color(54, 54, 54));
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 16));
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(btnCancelar.isEnabled())
					btnCancelar.setBackground(new Color(66, 211, 192));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(btnCancelar.isEnabled())
					btnCancelar.setBackground(new Color(54, 54, 54));
			}


			@Override
			public void mousePressed(MouseEvent e) {
				if(btnCancelar.isEnabled())
					btnCancelar.setBackground(new Color(54, 54, 54));
			}
			@Override
			public void mouseReleased(MouseEvent e) {

				if(btnCancelar.isEnabled())
					btnCancelar.setBackground(new Color(66, 211, 192));
			}
		});
		btnCancelar.setFocusTraversalKeysEnabled(false);
		btnCancelar.setFocusPainted(false);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framePrincipal.setVisible(true);
				getThis().dispose();
			}
		});
		panel.add(btnCancelar);

		JLabel lblRuta = new JLabel("Ruta:");
		lblRuta.setForeground(Color.WHITE);
		lblRuta.setFont(new Font("Arial", Font.BOLD, 17));
		lblRuta.setBounds(44, 311, 564, 14);
		panel.add(lblRuta);

		JButton btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1 = new JButton("");
		btnNewButton_1_1_1.setRolloverIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_DeleteSelected_32.png")));
		btnNewButton_1_1_1.setSelectedIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_Delete_32.png")));
		btnNewButton_1_1_1.setBackground(new Color(32,36,39));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framePrincipal.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1_1_1.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_Delete_32.png")));
		btnNewButton_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1_1_1.setFocusable(false);
		btnNewButton_1_1_1.setBorderPainted(false);
		btnNewButton_1_1_1.setBounds(665, 10, 24, 24);

		btnNewButton_1_1_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1_1_1.setForeground(Color.WHITE);
		btnNewButton_1_1_1.setBackground(new Color(32, 36, 39));
		btnNewButton_1_1_1.setBounds(668, 10, 24, 24);
		panel.add(btnNewButton_1_1_1);

		panel_1 = new JPanel();
		TitledBorder border =(new TitledBorder(new LineBorder(new Color(44, 133, 121)), "Ajustes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		border.setTitleFont(new Font("Arial", Font.PLAIN, 18));
		panel_1.setBorder(border);
		panel_1.setBounds(13, 79, 679, 222);
		panel_1.setBackground(new Color(32,36,39));
		panel.add(panel_1);
		panel_1.setLayout(null);

		spinnerTamBloque = new JSpinner();
		spinnerTamBloque.setToolTipText("Tamaño en bytes que tendra cada bloque de informacion.Se recomienda mantener por defecto");
		spinnerTamBloque.setBounds(370, 56, 286, 34);
		panel_1.add(spinnerTamBloque);
		spinnerTamBloque.setModel(new SpinnerNumberModel(700, 50, 5000, 10));
		spinnerTamBloque.setFont(new Font("Arial", Font.PLAIN, 18));
		spinnerTamBloque.setUI(BSUI);		
		spinnerTamBloque.setBorder(BorderFactory.createLineBorder(new Color(44,133,121)));
		((DefaultEditor) spinnerTamBloque.getEditor()).getTextField().setCaretColor(Color.white);


		JLabel lblTamannoBloque = new JLabel("Tamaño de bloque:\r\n");
		lblTamannoBloque.setBounds(370, 32, 238, 14);
		panel_1.add(lblTamannoBloque);
		lblTamannoBloque.setForeground(Color.WHITE);
		lblTamannoBloque.setFont(new Font("Arial", Font.BOLD, 17));

		spinnerVentana = new JSpinner();
		spinnerVentana.setToolTipText("Numero de bloques que se buscaran coincidencias.Se recomienda mantener por defecto");
		spinnerVentana.setBounds(370, 163, 286, 34);
		panel_1.add(spinnerVentana);
		spinnerVentana.setModel(new SpinnerNumberModel(500, 1, 5000, 10));
		spinnerVentana.setFont(new Font("Arial", Font.PLAIN, 18));
		spinnerVentana.setBorder(BorderFactory.createLineBorder(new Color(44,133,121)));
		spinnerVentana.setUI(BSUI2);
		((DefaultEditor) spinnerVentana.getEditor()).getTextField().setCaretColor(Color.white);

		JLabel lblVentana = new JLabel("Ventana de desplazamiento:");
		lblVentana.setBounds(376, 139, 232, 14);
		panel_1.add(lblVentana);
		lblVentana.setFont(new Font("Arial", Font.BOLD, 17));
		lblVentana.setForeground(Color.WHITE);

		textNombre = new JTextField();
		textNombre.setToolTipText("Nombre que tendra el archivo despues de comprimido");
		textNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!Validation.validarNombre(textNombre.getText())) {
					btnAceptar.setEnabled(false);
					textNombre.setBorder(BorderFactory.createLineBorder(Color.red));
				}
				else {
					btnAceptar.setEnabled(true);
					textNombre.setBorder(BorderFactory.createLineBorder(new Color(44,133,121)));
				}
			}
		});
		textNombre.setCaretColor(Color.white);
		textNombre.setText(Convert.removeExtension(fileProcesar.getName()));
		textNombre.setBounds(20, 56, 301, 34);
		panel_1.add(textNombre);
		textNombre.setFont(new Font("Arial", Font.PLAIN, 18));
		textNombre.setBackground(new Color(54,54,54));
		textNombre.setForeground(Color.WHITE);	
		textNombre.setBorder(BorderFactory.createLineBorder(new Color(44,133,121)));
		textNombre.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre del archivo:");
		lblNombre.setBounds(20, 32, 588, 14);
		panel_1.add(lblNombre);
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(new Font("Arial", Font.BOLD, 17));



		spinnerCoincidencia = new JSpinner();
		spinnerCoincidencia.setToolTipText("Numero minimo de coincidencias que se van a reducir.Se recomienda mantener por defecto");
		spinnerCoincidencia.setBounds(20, 163, 301, 34);
		panel_1.add(spinnerCoincidencia);
		spinnerCoincidencia.setModel(new SpinnerNumberModel(12, 12, 5000, 1));
		spinnerCoincidencia.setFont(new Font("Arial", Font.PLAIN, 18));
		spinnerCoincidencia.setBorder(BorderFactory.createLineBorder(new Color(44,133,121)));
		spinnerCoincidencia.setUI(BSUI3);
		((DefaultEditor) spinnerCoincidencia.getEditor()).getTextField().setCaretColor(Color.white);

		JLabel lblCoincidencia = new JLabel("Minimo de coincidencias:\r\n");
		lblCoincidencia.setBounds(24, 139, 584, 14);
		panel_1.add(lblCoincidencia);
		lblCoincidencia.setForeground(Color.WHITE);
		lblCoincidencia.setFont(new Font("Arial", Font.BOLD, 17));
		panel.add(getLblNewLabel());

		JLabel lbl_x = new JLabel("Archivo: ");
		lbl_x.setForeground(Color.WHITE);
		lbl_x.setFont(new Font("Arial", Font.BOLD, 17));
		lbl_x.setBounds(26, 409, 87, 34);
		panel.add(lbl_x);

		JLabel lblIcon = new JLabel(" "+fileProcesar.getName());
		lblIcon.setForeground(Color.WHITE);
		lblIcon.setFont(new Font("Arial", Font.BOLD, 17));
		lblIcon.setBounds(108, 409, 275, 34);
		panel.add(lblIcon);
		FileSystemView view = FileSystemView.getFileSystemView();
		Icon icon = view.getSystemIcon(fileProcesar);

		// Redimensionar el icono con el tamaño del JLabel
		ImageIcon iconoRedimensionado = new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(34, 34, Image.SCALE_SMOOTH));
		lblIcon.setIcon(iconoRedimensionado);

		JLabel lbl_x_1 = new JLabel("Estado: ");
		lbl_x_1.setForeground(Color.WHITE);
		lbl_x_1.setFont(new Font("Arial", Font.BOLD, 17));
		lbl_x_1.setBounds(393, 409, 87, 34);
		panel.add(lbl_x_1);

		lblProceso = new JLabel("Listo para procesar");
		lblProceso.setForeground(Color.WHITE);
		lblProceso.setFont(new Font("Arial", Font.BOLD, 17));
		lblProceso.setBounds(469, 409, 223, 34);
		panel.add(lblProceso);



	}
	public JDialog getThis() {
		return this;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Codman ");
			lblNewLabel.setIcon(new ImageIcon(Secundaria_Comprimir.class.getResource("/ICONS/icons8_Library_48.png")));
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
			lblNewLabel.setBounds(26, 10, 230, 59);
			;
		}
		return lblNewLabel;
	}
}
