package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;

import ourTools.Convert;
import javax.swing.border.LineBorder;

public class Principal extends JFrame {
	private JPanel panel;
	private JLabel lblNewLabel;
	private PanelRound panel_1;
	private JButton btnNewButton_1_1_1;
	private JLabel lblFile;
	private DropTarget dropTarget;
	private JLabel lbliconFile;
	private JLabel lblTamaño;
	private MiBoton btnAceptar;
	private MiBoton btnCancelar;
	private File fileProcesar=null;
	private boolean isCompress=false;
	private int posX, posY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/ICONS/icons8_Library_48.png")));

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setType(Type.POPUP);
		setBackground(new Color(32, 36 ,38));
		setForeground(SystemColor.activeCaption);
		setFont(new Font("Candara", Font.PLAIN, 15));
		setTitle("CODMAN");
		setBounds(100, 100, 700, 440);
		setLocationRelativeTo(null);
		getContentPane().add(getPanel(), BorderLayout.CENTER);


	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setEnabled(false);
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
			panel.setBorder(new LineBorder(new Color(44, 133, 121), 2));
			panel.setBackground(new Color(32, 36, 39));
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getPanel_1());
			panel.add(getBtnNewButton_1_1_1());
			panel.add(getBtnAceptar());
			panel.add(getBtnCancelar());
		}
		return panel;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Codman ");
			lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/icons8_Library_48.png")));
			
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
			lblNewLabel.setForeground(Color.WHITE);
			lblNewLabel.setBounds(27, 25, 230, 59);
		}
		return lblNewLabel;
	}
	private PanelRound getPanel_1() {
		panel_1 = new PanelRound();
		panel_1.setBorder(null);
		panel_1.setBounds(27, 94, 637, 230);
		panel_1.setBackground(new Color(54,54,54));
		panel_1.setColorBorder(new Color(44, 133 , 121));
		panel_1.setTamañoBorder(6);
		panel_1.setRoundBottomLeft(50);
		panel_1.setRoundBottomRight(50);
		panel_1.setRoundTopLeft(50);
		panel_1.setRoundTopRight(50);	
		panel_1.setLayout(null);
		panel_1.add(getLabel_1());
		panel_1.add(getLabel_2());
		panel_1.add(getLblTamaño());
		dropTarget = new DropTarget(panel_1, new DropTargetAdapter() {
			public void drop(DropTargetDropEvent event) {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				Transferable transferable = event.getTransferable();
				if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					try {
						List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
						if (!files.isEmpty()) {
							fileProcesar = files.get(0);
							if(!fileProcesar.isDirectory() && !esAccesoDirecto(fileProcesar)) {
								lblFile.setText(fileProcesar.getName());
								lblTamaño.setText("Tamaño: "+Convert.obtenerTamanoConUnidad(fileProcesar.length()));
								FileSystemView view = FileSystemView.getFileSystemView();
								Icon icono = view.getSystemIcon(fileProcesar);
								btnAceptar.setEnabled(true);
								btnCancelar.setEnabled(true);

								if(Convert.obtenerExtension(fileProcesar.getName()).equals("cod")) {
									btnAceptar.setText("Descomprimir");
									isCompress=false;
								}else {
									btnAceptar.setText("Comprimir");
									isCompress=true;
								}

								// Obtener el tamaño del JLabel
								int ancho = lbliconFile.getWidth();
								int alto = lbliconFile.getHeight();

								// Redimensionar el icono con el tamaño del JLabel
								ImageIcon iconoRedimensionado = new ImageIcon(((ImageIcon) icono).getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
								lbliconFile.setIcon(iconoRedimensionado);
							}else {
								btnAceptar.setEnabled(false);
								btnCancelar.setEnabled(false);
								lbliconFile.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/addFile_64.png")));
								fileProcesar=null;
								lblFile.setText("Arrastre el archivo aqui");
								lblTamaño.setText("Error: No debe arrastrar una carpeta ni un acceso directo");
								
							}
						}
					} catch (UnsupportedFlavorException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});


		return panel_1;
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
			btnNewButton_1_1_1.setBounds(665, 10, 24, 24);
		}
		return btnNewButton_1_1_1;
	}

	
	private JLabel getLabel_1() {
		if (lblFile == null) {
			lblFile =  new JLabel("Arrastra el archivo aqui");
			lblFile.setHorizontalAlignment(SwingConstants.CENTER);
			lblFile.setForeground(Color.WHITE);
			lblFile.setFont(new Font("Arial", Font.BOLD, 20));
			lblFile.setBounds(10, 127, 617, 40);
		}
		return lblFile;
	}
	private JLabel getLabel_2() {
		if (lbliconFile == null) {
			lbliconFile = new JLabel("");
			lbliconFile.setHorizontalTextPosition(SwingConstants.CENTER);
			lbliconFile.setHorizontalAlignment(SwingConstants.CENTER);
			lbliconFile.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/addFile_64.png")));
			lbliconFile.setBounds(266, 35, 89, 82);
		}
		return lbliconFile;
	}
	private JLabel getLblTamaño() {
		if (lblTamaño == null) {
			lblTamaño = new JLabel("");
			lblTamaño.setHorizontalAlignment(SwingConstants.CENTER);
			lblTamaño.setForeground(SystemColor.menu);
			lblTamaño.setFont(new Font("Arial", Font.BOLD, 11));
			lblTamaño.setBounds(10, 163, 617, 40);
		}
		return lblTamaño;
	}
	private MiBoton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new MiBoton();
			btnAceptar.setEnabled(false);
			btnAceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog principal;
					if(isCompress)
					 principal = new Secundaria_Comprimir(fileProcesar, getThis());
					else
						principal = new Secundaria_Descomprimir(true, fileProcesar, getThis());
					principal.setModal(true);
					getThis().setVisible(false);
					principal.setResizable(false);
					principal.setLocationRelativeTo(getThis());
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					principal.setVisible(true);
				
					
				}
			});
			btnAceptar.setFocusTraversalKeysEnabled(false);
			btnAceptar.setFocusPainted(false);
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

			btnAceptar.setForeground(Color.WHITE);
			btnAceptar.setFont(new Font("Arial", Font.BOLD, 16));
			btnAceptar.setBounds(367, 355, 293, 36);
			btnAceptar.setText("Aceptar");
			btnAceptar.setRadio(25);
		}
		return btnAceptar;
	}
	private MiBoton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new MiBoton();
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAceptar.setEnabled(false);
					btnCancelar.setEnabled(false);
					lbliconFile.setIcon(new ImageIcon(Principal.class.getResource("/ICONS/addFile_64.png")));
					fileProcesar=null;
					lblFile.setText("Arrastre el archivo aqui");
					lblTamaño.setText("");
				}
			});
			btnCancelar.setEnabled(false);
			btnCancelar.setText("Cancelar");
			btnCancelar.setRadio(25);
			btnCancelar.setForeground(Color.WHITE);

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
			btnCancelar.setBounds(27, 355, 293, 36);
		}
		return btnCancelar;
	}
	
	public static boolean esAccesoDirecto(File archivo) {
        String extension= Convert.obtenerExtension(archivo.getName());
        return extension.equals("lnk");
    }
	public JFrame getThis() {
		return this;
	}
}
class ImagePanel extends JPanel {
	private Image image;
	

	public ImagePanel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void paint(Graphics g) {
		image = new ImageIcon(getClass().getResource("Fondo.png")).getImage();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		setOpaque(false);
		
		super.paint(g);
	}

	

}

