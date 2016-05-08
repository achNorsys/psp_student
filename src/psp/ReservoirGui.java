package psp;

public class ReservoirGui {
	private String hauteur;
	private String longeur;
	private String largeur;
	private String h0supp;
	private String h0Inf;
	private String deltah;

	public ReservoirGui(String hauteur, String longeur, String largeur, String h0supp, String h0Inf, String deltah) {
		this.hauteur = hauteur;
		this.longeur = longeur;
		this.largeur = largeur;
		this.h0supp = h0supp;
		this.h0Inf = h0Inf;
		this.deltah = deltah;
	}

	public String getHauteur() {
		return hauteur;
	}

	public void setHauteur(String hauteur) {
		this.hauteur = hauteur;
	}

	public String getLongeur() {
		return longeur;
	}

	public void setLongeur(String longeur) {
		this.longeur = longeur;
	}

	public String getLargeur() {
		return largeur;
	}

	public void setLargeur(String largeur) {
		this.largeur = largeur;
	}

	public String getH0supp() {
		return h0supp;
	}

	public void setH0supp(String h0supp) {
		this.h0supp = h0supp;
	}

	public String getH0Inf() {
		return h0Inf;
	}

	public void setH0Inf(String h0Inf) {
		this.h0Inf = h0Inf;
	}

	public String getDeltah() {
		return deltah;
	}

	public void setDeltah(String deltah) {
		this.deltah = deltah;
	}

	
	
}
