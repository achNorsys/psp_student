package psp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChangementCoutAmmortissement extends MouseAdapter {
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		gui.mip.setAmmortissement(!gui.mip.isAmmortissement());
		if(gui.mip.isAmmortissement()){
			gui.coutAmmortissement.setVisible(true);
		}
		else{
			gui.coutAmmortissement.setVisible(false);
		}
	}
}
