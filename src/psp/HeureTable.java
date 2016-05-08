
package psp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HeureTable extends AbstractTableModel {
    private List<Cout> couts = new ArrayList<Cout>();
 
    private final String[] entetes = {"Heure", "Cout electricite", "cout regulation"};
 
    public HeureTable() {
        super();
        
    }
 
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(columnIndex == 0)
    		return false; 
    	else
    		return true;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    	Cout cout = couts.get(rowIndex);
        if(aValue != null){
        	switch(columnIndex){
	            case 1:
	            	cout.setCoutElectricite((String)aValue);
	            break;
	            case 2:
	            	cout.setCoutRegulation((String)aValue);
	            break;
        	}
        }
        if(cout.getCoutElectricite().equals(""))
        	cout.setCoutElectricite("0.0");
        if(cout.getCoutRegulation().equals(""))
        	cout.setCoutRegulation("0.0");
    }
    
    public int getRowCount() {
        return couts.size();
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return couts.get(rowIndex).getIndex();
            case 1:
                return couts.get(rowIndex).getCoutElectricite();
            case 2:
                return couts.get(rowIndex).getCoutRegulation();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
 
    public void addCout(Cout cout) {
    	couts.add(cout);
 
        fireTableRowsInserted(couts.size() -1, couts.size() -1);
    }
 
    public void removeAmi(int rowIndex) {
    	couts.remove(rowIndex); 
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    public void removeAll() {
    	couts.clear();
    }
    public void resetIndex(){
    	 List<Cout> copyCouts = new ArrayList<>();
    	 for (int i = 0; i < couts.size(); i++) {
			Cout cout = couts.get(i);
			cout.setIndex(i+1);
			copyCouts.add(cout);
		}
    	 couts = copyCouts;
    }
}
