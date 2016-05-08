package psp;

public class Cout {
	private Integer index;
	private String coutElectricite;
	private String coutRegulation;
public Cout(Integer index,String coutElectricite,String coutRegulation) {
	this.coutElectricite =coutElectricite;
	this.coutRegulation =coutRegulation;
	this.index = index;
}
public Integer getIndex() {
	return index;
}
public void setIndex(Integer index) {
	this.index = index;
}
public String getCoutElectricite() {
	return coutElectricite;
}
public void setCoutElectricite(String cout) {
	this.coutElectricite = cout;
}
public String getCoutRegulation() {
	return coutRegulation;
}
public void setCoutRegulation(String coutRegulation) {
	this.coutRegulation = coutRegulation;
}


}
