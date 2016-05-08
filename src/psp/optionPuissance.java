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

public class optionPuissance extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField puissanceMaxT;
	private JTextField puissanceMinT;
	private JTextField alphaT;
	private JTextField puissanceMaxP;
	private JTextField puissanceMinP;
	private JTextField alphaP;

	/**
	 * Create the dialog.
	 */
	public optionPuissance() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				gui.frame.enable();
			    dispose();
			}
		});
		setTitle("Configuration Puissance");
		setBounds(100, 100, 379, 243);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 376, 167);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Puissance max");
			lblNewLabel.setBounds(29, 52, 74, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			puissanceMaxT = new JTextField();
			puissanceMaxT.setText(gui.puissance.getpMaxT());
			puissanceMaxT.setBounds(133, 49, 86, 20);
			contentPanel.add(puissanceMaxT);
			puissanceMaxT.setColumns(10);
		}
		{
			JLabel lblPuissanceMin = new JLabel("Puissance min");
			lblPuissanceMin.setBounds(29, 84, 74, 14);
			contentPanel.add(lblPuissanceMin);
		}
		{
			puissanceMinT = new JTextField();
			puissanceMinT.setText(gui.puissance.getpMinT());
			puissanceMinT.setColumns(10);
			puissanceMinT.setBounds(133, 81, 86, 20);
			contentPanel.add(puissanceMinT);
		}
		{
			JLabel lblAlphaT = new JLabel("Alpha ");
			lblAlphaT.setBounds(29, 118, 49, 14);
			contentPanel.add(lblAlphaT);
		}
		{
			alphaT = new JTextField();
			alphaT.setText(gui.puissance.getAlphaT());
			alphaT.setColumns(10);
			alphaT.setBounds(133, 115, 86, 20);
			contentPanel.add(alphaT);
		}
		{
			puissanceMaxP = new JTextField();
			puissanceMaxP.setText(gui.puissance.getpMaxP());
			puissanceMaxP.setColumns(10);
			puissanceMaxP.setBounds(255, 49, 86, 20);
			contentPanel.add(puissanceMaxP);
		}
		{
			puissanceMinP = new JTextField();
			puissanceMinP.setText(gui.puissance.getpMinP());
			puissanceMinP.setColumns(10);
			puissanceMinP.setBounds(255, 81, 86, 20);
			contentPanel.add(puissanceMinP);
		}
		{
			alphaP = new JTextField();
			alphaP.setText(gui.puissance.getAlphaP());
			alphaP.setColumns(10);
			alphaP.setBounds(255, 115, 86, 20);
			contentPanel.add(alphaP);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Turbine");
			lblNewLabel_1.setBounds(152, 24, 46, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Pompe");
			lblNewLabel_2.setBounds(277, 24, 46, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 164, 363, 33);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gui.puissance = new Puissance(puissanceMaxT.getText(), puissanceMaxP.getText(), puissanceMinP.getText(), puissanceMinT.getText(), alphaT.getText(), alphaP.getText());
						if(puissanceMaxT.getText().equals("")){
							gui.puissance.setpMaxT("0.0");
						}
						if(puissanceMaxP.getText().equals("")){
							gui.puissance.setpMaxP("0.0");
						}
						if(puissanceMinP.getText().equals("")){
							gui.puissance.setpMinT("0.0");
						}
						if(puissanceMinT.getText().equals("")){
							gui.puissance.setpMinP("0.0");
						}
						if(alphaT.getText().equals("")){
							gui.puissance.setAlphaT("0.0");
						}
						if(alphaP.getText().equals("")){
							gui.puissance.setAlphaP("0.0");
						}
						gui.frame.enable();
						dispose();
					}
				});
				okButton.setBounds(201, 5, 58, 23);
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
				cancelButton.setBounds(269, 5, 84, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	 
	}

}
