package psp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class changementListener extends MouseAdapter {
	private JPanel coutChangementPannel;
  public changementListener(JPanel coutChangementPannel) {
	this.coutChangementPannel =coutChangementPannel;
}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		gui.mip.setCoutChangement(!gui.mip.isCoutChangement());
		if(gui.mip.isCoutChangement()){
			coutChangementPannel.setVisible(true);
		}
		else{
			coutChangementPannel.setVisible(false);
		}
	}
}
