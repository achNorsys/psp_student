package psp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import eu.hansolo.custom.SteelCheckBox;
import eu.hansolo.tools.ColorDef;
import ilog.concert.IloException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gui {

	static public JFrame frame;
	static public ResultatGui resultatGui;
	static public Mip mip;
	static public JTextField nbTurbine;
	static public HeureTable modele = new HeureTable();
	private JTable tableau;
	static public JTextField aVt;
	static public JTextField tVa;
	static public JTextField aVp;
	static public JTextField pVa;
	static public Puissance puissance = new Puissance("0.0", "0.0", "0.0", "0.0", "0.0", "0.0");
	static public ReservoirGui reservoirGui = new ReservoirGui("100.0", "1000.0", "1000.0", "0.0", "100.0", "500.0");
	static public JTextField heureColding;
	static public JTextField coutAmmortissement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Instance instance;
		try {
			instance = Parser.lireInstance("Data" + File.separator + "Instances" + File.separator + "instance10.txt",
					1);
			mip = new Mip(instance);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 655, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel configMachinePanel = new JPanel();
		configMachinePanel.setBounds(10, 39, 287, 328);
		configMachinePanel.setBorder(BorderFactory.createTitledBorder("Configuration Pompe-Turbine"));
		frame.getContentPane().add(configMachinePanel);
		configMachinePanel.setLayout(null);

		JPanel coutChangementPannel = new JPanel();
		coutChangementPannel.setBounds(17, 186, 227, 119);
		configMachinePanel.add(coutChangementPannel);
		coutChangementPannel.setLayout(null);

		JButton btnRsoudre = new JButton("R\u00E9soudre");
		btnRsoudre.setBounds(51, 389, 89, 23);
		btnRsoudre.addActionListener(new SolveListener());
		frame.getContentPane().add(btnRsoudre);
		configMachinePanel.add(initMode());

		nbTurbine = new JTextField();
		nbTurbine.setBounds(124, 27, 47, 20);
		nbTurbine.setText("1");
		configMachinePanel.add(nbTurbine);
		nbTurbine.setColumns(10);

		JLabel lblNombreDeTurbine = new JLabel("Nombre de Machine");
		lblNombreDeTurbine.setBounds(17, 30, 112, 14);
		configMachinePanel.add(lblNombreDeTurbine);

		JLabel lblModeInitial = new JLabel("Mode Initial");
		lblModeInitial.setBounds(17, 55, 95, 22);
		configMachinePanel.add(lblModeInitial);

		SteelCheckBox coutChangement = new SteelCheckBox();
		coutChangement.setBounds(17, 153, 132, 26);
		configMachinePanel.add(coutChangement);
		coutChangement.setSelected(mip.isCoutChangement());
		if (mip.isCoutChangement()) {
			coutChangementPannel.setVisible(true);
		} else {
			coutChangementPannel.setVisible(false);
		}
		coutChangement.setSelectedColor(ColorDef.GREEN);
		coutChangement.setColored(true);
		coutChangement.setText("cout changement");
		SteelCheckBox refroidissement = new SteelCheckBox();
		refroidissement.setBounds(17, 124, 125, 26);
		configMachinePanel.add(refroidissement);

		refroidissement.setSelected(mip.isRefroidissement());
		refroidissement.setColored(true);
		refroidissement.setSelectedColor(ColorDef.GREEN);
		refroidissement.setText("refroidissement");

		JLabel lblArretVersTurbine = new JLabel("Arret Vers Turbine");
		lblArretVersTurbine.setBounds(10, 14, 111, 14);
		coutChangementPannel.add(lblArretVersTurbine);

		aVt = new JTextField();
		aVt.setColumns(10);
		aVt.setBounds(131, 11, 47, 20);
		aVt.setText("0.0");
		coutChangementPannel.add(aVt);
		JLabel lblTurbineVersArret = new JLabel("Turbine Vers Arret");
		lblTurbineVersArret.setBounds(10, 39, 111, 14);
		coutChangementPannel.add(lblTurbineVersArret);

		tVa = new JTextField();
		tVa.setColumns(10);
		tVa.setBounds(131, 36, 47, 20);
		tVa.setText("0.0");
		coutChangementPannel.add(tVa);

		JLabel lblArretVersPompe = new JLabel("Arret vers Pompe");
		lblArretVersPompe.setBounds(10, 66, 111, 14);
		coutChangementPannel.add(lblArretVersPompe);

		aVp = new JTextField();
		aVp.setColumns(10);
		aVp.setBounds(131, 63, 47, 20);
		aVp.setText("0.0");
		coutChangementPannel.add(aVp);

		JLabel lblPompeVersArret = new JLabel("Pompe vers Arret");
		lblPompeVersArret.setBounds(10, 91, 111, 14);
		coutChangementPannel.add(lblPompeVersArret);

		pVa = new JTextField();
		pVa.setColumns(10);
		pVa.setBounds(131, 91, 47, 20);
		pVa.setText("0.0");
		coutChangementPannel.add(pVa);
		refroidissement.addMouseListener(new refroidissementListener());
		coutChangement.addMouseListener(new changementListener(coutChangementPannel));
		
		heureColding = new JTextField();
		heureColding.setText("2");
		heureColding.setColumns(10);
		heureColding.setBounds(175, 127, 57, 20);
		configMachinePanel.add(heureColding);
		
		coutAmmortissement = new JTextField();
		coutAmmortissement.setText("-430000");
		coutAmmortissement.setColumns(10);
		coutAmmortissement.setBounds(175, 99, 57, 20);
		configMachinePanel.add(coutAmmortissement);
		
		SteelCheckBox stlchckbxCoutAmmortissement = new SteelCheckBox();
		stlchckbxCoutAmmortissement.setText("cout ammortissement");
		stlchckbxCoutAmmortissement.setSelectedColor(ColorDef.GREEN);
		stlchckbxCoutAmmortissement.setSelected(mip.isAmmortissement());
		stlchckbxCoutAmmortissement.setColored(true);
		stlchckbxCoutAmmortissement.setBounds(17, 96, 152, 26);
		stlchckbxCoutAmmortissement.addMouseListener(new ChangementCoutAmmortissement());
		configMachinePanel.add(stlchckbxCoutAmmortissement);

		tableau = new JTable(modele);
		tableau.setVisible(true);
		JScrollPane coutPanel = new JScrollPane(tableau);
		coutPanel.setBounds(348, 39, 295, 328);
		coutPanel.setBorder(BorderFactory.createTitledBorder("Configuration cout horraire"));
		JPanel boutons = new JPanel();
		boutons.add(new JButton(new RemoveAction()));
		frame.getContentPane().add(coutPanel);

		JButton ajouter = new JButton(new AddAction());
		ajouter.setBounds(511, 389, 118, 23);
		frame.getContentPane().add(ajouter);

		JButton supprimer = new JButton(new RemoveAction());
		supprimer.setBounds(382, 389, 119, 23);
		frame.getContentPane().add(supprimer);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fichierMenu = new JMenu("Fichier");
		JMenuItem loadFile = new JMenuItem("charger Fichier");
		JMenuItem saveFile = new JMenuItem("sauvegarder Fichier");
		saveFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(gui.frame.getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					File file = fc.getSelectedFile();
					SolveListener.writeConfig(file.getAbsolutePath());
				}
			}
		});
		loadFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(gui.frame.getContentPane());
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					try {
						File file = fc.getSelectedFile();
						chargerFichier(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		fichierMenu.add(loadFile);
		fichierMenu.add(saveFile);
		menuBar.add(fichierMenu);

		JMenu optionMenu = new JMenu("Options");

		JMenuItem configPuissance = new JMenuItem("Configuration puissance");
		configPuissance.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				optionPuissance dialog = new optionPuissance();
				dialog.setVisible(true);
				gui.frame.disable();
			}
		});
		JMenuItem configReservoir = new JMenuItem("Configuration reservoir");
		configReservoir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				OptionReservoir dialog = new OptionReservoir();
				dialog.setVisible(true);
				gui.frame.disable();
			}
		});
		optionMenu.add(configPuissance);
		optionMenu.add(configReservoir);
		menuBar.add(optionMenu);

		// panel.add(new JScrollPane(tableau), BorderLayout.CENTER);

	}

	private JComboBox<modeEnum> initMode() {
		JComboBox<modeEnum> comboBox = new JComboBox<modeEnum>();
		comboBox.setBounds(90, 55, 83, 22);
		comboBox.addItem(modeEnum.ARRET);
		comboBox.addItem(modeEnum.POMPE);
		comboBox.addItem(modeEnum.TURBINE);
		if (mip.getMODE_POMPE_INIT() == 0) {
			if (mip.getMODE_TURBINE_INIT() == 0) {
				comboBox.setSelectedItem(modeEnum.ARRET);
			} else {
				comboBox.setSelectedItem(modeEnum.TURBINE);
			}
		} else {
			comboBox.setSelectedItem(modeEnum.POMPE);
		}
		comboBox.addActionListener(new modeInitListener(comboBox));
		return comboBox;
	}

	private class RemoveAction extends AbstractAction {
		private RemoveAction() {
			super("Supprimmer");
		}

		public void actionPerformed(java.awt.event.ActionEvent arg0) {
			int[] selection = tableau.getSelectedRows();

			for (int i = selection.length - 1; i >= 0; i--) {
				modele.removeAmi(selection[i]);
			}
			modele.resetIndex();
		}
	}

	private class AddAction extends AbstractAction {
		private AddAction() {
			super("Ajouter");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			modele.addCout(new Cout(modele.getRowCount() + 1, "0.0", "0.0"));

		}
	}

	private void chargerFichier(File file) throws IOException {
		Instance instance = Parser.lireInstance(file.getAbsolutePath().toString(), 1);
		gui.modele.removeAll();
		for (int i = 0; i < instance.getCout().length; i++) {
			gui.modele.addCout(new Cout(i, String.valueOf(instance.getCout()[i]), String.valueOf(instance.getRegulation()[i])));
		}
		aVt.setText(String.valueOf(instance.getTPs()[0].getC_AT()));
		pVa.setText(String.valueOf(instance.getTPs()[0].getC_PA()));
		aVp.setText(String.valueOf(instance.getTPs()[0].getC_AP()));
		tVa.setText(String.valueOf(instance.getTPs()[0].getC_TA()));
		gui.puissance.setAlphaP(String.valueOf(instance.getTPs()[0].getAlpha_P()));
		gui.puissance.setAlphaT(String.valueOf(instance.getTPs()[0].getAlpha_T()));
		gui.puissance.setpMaxP(String.valueOf(instance.getTPs()[0].getP_P_max()));
		gui.puissance.setpMaxT(String.valueOf(instance.getTPs()[0].getP_T_max()));
		gui.puissance.setpMinP(String.valueOf(instance.getTPs()[0].getP_P_min()));
		gui.puissance.setpMinT(String.valueOf(instance.getTPs()[0].getP_T_min()));
		gui.reservoirGui.setDeltah(String.valueOf(instance.getDelta_H()));
		gui.reservoirGui.setH0Inf(String.valueOf(instance.getInf().getH_0()));
		gui.reservoirGui.setH0supp(String.valueOf(instance.getSup().getH_0()));
		gui.reservoirGui.setLargeur(String.valueOf(instance.getInf().getLargeur()));
		gui.reservoirGui.setLongeur(String.valueOf(instance.getInf().getLongueur()));
		gui.reservoirGui.setHauteur(String.valueOf(instance.getInf().getHauteur()));
	}
}
