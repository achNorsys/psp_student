package psp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class modeInitListener implements ActionListener {
	private JComboBox comboBox;
	public modeInitListener(JComboBox comboBox) {
		this.comboBox = comboBox;
	}
	public void actionPerformed(ActionEvent arg0) {
		modeEnum selectItem = (modeEnum) comboBox.getSelectedItem();
		switch (selectItem) {
		case ARRET:
			gui.mip.setMODE_POMPE_INIT(0);
			gui.mip.setMODE_TURBINE_INIT(0);
			break;
		case TURBINE:
			gui.mip.setMODE_POMPE_INIT(0);
			gui.mip.setMODE_TURBINE_INIT(1);
			break;
		case POMPE:
			gui.mip.setMODE_POMPE_INIT(1);
			gui.mip.setMODE_TURBINE_INIT(0);
			break;
		default:
			break;
		}
	}
}
