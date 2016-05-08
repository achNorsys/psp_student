package psp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionReservoir extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField hauteur;
	private JTextField longueur;
	private JTextField largeur;
	private JTextField h0sup;
	private JTextField h0inf;
	private JTextField deltah;

	/**
	 * Create the dialog.
	 */
	public OptionReservoir() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				gui.frame.enable();
			    dispose();
			}
		});
		setTitle("Configuration Puissance");
		setBounds(100, 100, 262, 349);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 246, 271);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Hauteur");
			lblNewLabel.setBounds(29, 52, 74, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			hauteur = new JTextField();
			hauteur.setText(gui.reservoirGui.getHauteur());
			hauteur.setBounds(133, 49, 86, 20);
			contentPanel.add(hauteur);
			hauteur.setColumns(10);
		}
		{
			JLabel lblPuissanceMin = new JLabel("Longeur");
			lblPuissanceMin.setBounds(29, 84, 74, 14);
			contentPanel.add(lblPuissanceMin);
		}
		{
			longueur = new JTextField();
			longueur.setText(gui.reservoirGui.getLongeur());
			longueur.setColumns(10);
			longueur.setBounds(133, 81, 86, 20);
			contentPanel.add(longueur);
		}
		{
			JLabel lblAlphaT = new JLabel("Largeur");
			lblAlphaT.setBounds(29, 118, 49, 14);
			contentPanel.add(lblAlphaT);
		}
		{
			largeur = new JTextField();
			largeur.setText(gui.reservoirGui.getLargeur());
			largeur.setColumns(10);
			largeur.setBounds(133, 115, 86, 20);
			contentPanel.add(largeur);
		}
		{
			h0sup = new JTextField();
			h0sup.setText(gui.reservoirGui.getH0supp());
			h0sup.setColumns(10);
			h0sup.setBounds(133, 146, 86, 20);
			contentPanel.add(h0sup);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("H_0_sup");
			lblNewLabel_2.setBounds(29, 149, 46, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblHinf = new JLabel("H_0_inf");
			lblHinf.setBounds(29, 177, 46, 20);
			contentPanel.add(lblHinf);
		}
		{
			h0inf = new JTextField();
			h0inf.setBounds(133, 177, 86, 20);
			contentPanel.add(h0inf);
			h0inf.setText(gui.reservoirGui.getH0Inf());
			h0inf.setColumns(10);
		}
		{
			deltah = new JTextField();
			deltah.setBounds(133, 208, 86, 20);
			contentPanel.add(deltah);
			deltah.setText(gui.reservoirGui.getDeltah());
			deltah.setColumns(10);
		}
		{
			JLabel lblDeltah = new JLabel("Delta_H");
			lblDeltah.setBounds(29, 208, 46, 14);
			contentPanel.add(lblDeltah);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 269, 231, 33);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gui.reservoirGui = new ReservoirGui(hauteur.getText(), longueur.getText(), largeur.getText(), h0sup.getText(), h0inf.getText(), deltah.getText());
						if(hauteur.getText().equals("")){
							gui.reservoirGui.setHauteur("100.0");
						}
						if(h0sup.getText().equals("")){
							gui.reservoirGui.setH0supp("0.0");
						}
						if(h0inf.getText().equals("")){
							gui.reservoirGui.setH0Inf("100.0");
						}
						if(longueur.getText().equals("")){
							gui.reservoirGui.setLongeur("1000.0");
						}
						if(largeur.getText().equals("")){
							gui.reservoirGui.setLargeur("1000.0");
						}
						if(deltah.getText().equals("")){
							gui.reservoirGui.setDeltah("500.0");
						}
						gui.frame.enable();
						dispose();
					}
				});
				okButton.setBounds(62, 5, 58, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gui.frame.enable();
						dispose();
					}
				});
				cancelButton.setBounds(130, 5, 84, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	 
	}

}
