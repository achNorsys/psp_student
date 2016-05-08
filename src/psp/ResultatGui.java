package psp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import ilog.concert.IloException;

public class ResultatGui extends JDialog {

	private final JPanel contentPanel = new JPanel();
	static public SolutionTable modele = new SolutionTable();
	private JTable tableau;

	/**
	 * Create the dialog.
	 */
	public ResultatGui() {
		setBounds(100, 100, 734, 343);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			tableau = new JTable(modele);
			tableau.setVisible(true);
			JScrollPane coutPanel = new JScrollPane(tableau);
			coutPanel.setBounds(348, 81, 295, 328);

			getContentPane().add(coutPanel);
			try {
				if (gui.mip.model.solve()) {
					modele.removeAll();
					coutPanel.setBorder(BorderFactory.createTitledBorder("Bénifice: " + gui.mip.model.getObjValue()));

					for (int turbineCourante = 0; turbineCourante < gui.mip.instance
							.getTPs().length; turbineCourante++) {

						for (int heureCourante = 1; heureCourante < gui.mip.instance
								.getCout().length; heureCourante++) {
							Resultat resultat = new Resultat();
							resultat.setNumMachine(turbineCourante + 1 + "");
							resultat.setIndex(heureCourante + "");
							if (gui.mip.model.getValue(gui.mip.modePompe[turbineCourante][heureCourante]) > 0.9) {
								resultat.setMode("Pompe");
								resultat.setPuissance(
										gui.mip.model.getValue(gui.mip.puissancePompe[turbineCourante][heureCourante])
												+ "");
							} else if (gui.mip.model
									.getValue(gui.mip.modeTurbine[turbineCourante][heureCourante]) > 0.9) {
								resultat.setMode("Turbine");
								resultat.setPuissance(
										gui.mip.model.getValue(gui.mip.puissanceTurbine[turbineCourante][heureCourante])
												+ "");
							} else {
								resultat.setMode("Arret");
							}
							if (gui.mip.isCoutChangement()) {
								if (gui.mip.model.getValue(gui.mip.coutPtoA[turbineCourante][heureCourante]) < 0.0
										&& gui.mip.model
												.getValue(gui.mip.coutAtoT[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Pompe vers Turbine");
								} else if (gui.mip.model
										.getValue(gui.mip.coutTtoA[turbineCourante][heureCourante]) < 0.0
										&& gui.mip.model
												.getValue(gui.mip.coutAtoP[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Turbine vers Pompe");
								} else if (gui.mip.model
										.getValue(gui.mip.coutAtoP[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Arret vers Pompe");
								} else if (gui.mip.model
										.getValue(gui.mip.coutPtoA[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Pompe vers Arret ");
								} else if (gui.mip.model
										.getValue(gui.mip.coutAtoT[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Arret vers Turbine");
								} else if (gui.mip.model
										.getValue(gui.mip.coutTtoA[turbineCourante][heureCourante]) < 0.0) {
									resultat.setChangement("Turbine vers Arret ");
								}
							}
							resultat.setHauteurChute(
									gui.mip.model.getValue(gui.mip.hauteurChute[turbineCourante][heureCourante]) + "");

							modele.addCout(resultat);

						}
					}
				}
			} catch (IloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
