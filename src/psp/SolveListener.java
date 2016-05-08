package psp;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JMenuItem;

import ilog.concert.IloException;
import javafx.event.ActionEvent;

public class SolveListener implements ActionListener {

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
			try {
				String path = "Data"+File.separator+"Instances"+File.separator+"guiConfig.txt";
				 writeConfig(path);
				try {
					Instance instance = Parser.lireInstance(path,Integer.parseInt(gui.nbTurbine.getText()));
					gui.mip.instance = instance;
					gui.mip.initModel();
					gui.mip.solve();
					gui.resultatGui = new ResultatGui();
					gui.resultatGui.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} catch (IloException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}
	public static void writeConfig(String path){
		PrintWriter writer;
		try {
			writer = new PrintWriter(path, "UTF-8");
			writer.println("// Instance de test realiste");
			writer.println("");
			writer.println("// Caracteristique de la Turbine-Pompe puissance en MW");
			writer.println("P_T_min : "+gui.puissance.getpMinT()+";");
			writer.println("P_T_max : "+gui.puissance.getpMaxT()+";");
			writer.println("P_P_min : "+gui.puissance.getpMinP()+";");
			writer.println("P_P_max : "+gui.puissance.getpMaxP()+";");
			writer.println("alpha_T : "+gui.puissance.getAlphaT()+";");
			writer.println("alpha_P : "+gui.puissance.getAlphaP()+";");
			writer.println("c_AT : "+gui.aVt.getText()+";");
			writer.println("c_TA : "+gui.tVa.getText()+";");
			writer.println("c_AP : "+gui.aVp.getText()+";");
			writer.println("c_PA : "+gui.pVa.getText()+";");
	        writer.println("");
			writer.println("// Caracteristique des reservoirs distance en m");
			writer.println("Hauteur : "+gui.reservoirGui.getHauteur()+";");
			writer.println("Longeur : "+gui.reservoirGui.getLongeur()+";");
			writer.println("Largeur : "+gui.reservoirGui.getLargeur()+";");
			writer.println("H_0_sup : "+gui.reservoirGui.getH0supp()+";");
			writer.println("H_0_inf : "+gui.reservoirGui.getH0Inf()+";");
			writer.println("Delta_H : "+gui.reservoirGui.getDeltah()+";");
	        writer.println("");
			writer.println("// Prix horaire de l'electricite en �/MWh");
			writer.print("c_t : [0.0");
			for (int i = 0; i < gui.modele.getRowCount(); i++) {
				writer.print(","+gui.modele.getValueAt(i, 1));
			}
			writer.println("]");
			writer.println("// Prix horaire de la regulation en �/MWh");
			writer.print("r_t : [0.0");
			for (int i = 0; i < gui.modele.getRowCount(); i++) {
				writer.print(","+gui.modele.getValueAt(i, 2));
			}
			writer.print("];");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
