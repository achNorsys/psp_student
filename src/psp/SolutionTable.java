
package psp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class SolutionTable extends AbstractTableModel {
	private List<Resultat> resultats = new ArrayList<Resultat>();

	private final String[] entetes = { "Numero machine", "Heure", "Mode","Changement", "Puissance", "HauteurChute" };

	public SolutionTable() {
		super();

	}

	public int getRowCount() {
		return resultats.size();
	}

	public int getColumnCount() {
		return entetes.length;
	}

	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return resultats.get(rowIndex).getNumMachine();
		case 1:
			return resultats.get(rowIndex).getIndex();
		case 2:
			return resultats.get(rowIndex).getMode();
		case 3:
			return resultats.get(rowIndex).getChangement();
		case 4:
			return resultats.get(rowIndex).getPuissance();		
		case 5:
			return resultats.get(rowIndex).getHauteurChute();
		default:
			return null; // Ne devrait jamais arriver
		}
	}

	public void addCout(Resultat resultat) {
		resultats.add(resultat);

		fireTableRowsInserted(resultats.size(), resultats.size());
	}
	public void removeAll() {
		resultats.clear();
	}

}
