package psp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class refroidissementListener extends MouseAdapter {
  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		gui.mip.setRefroidissement(!gui.mip.isRefroidissement());
		if(gui.mip.isRefroidissement()){
			gui.heureColding.setVisible(true);
		}
		else{
			gui.heureColding.setVisible(false);
		}
	}
}
