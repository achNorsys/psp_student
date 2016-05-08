package psp;

public class Puissance {
	private String pMaxT;
	private String pMaxP;
	private String pMinP;
	private String pMinT;
	private String alphaT;
	private String alphaP;

	public Puissance(String pMaxT, String pMaxP, String pMinP, String pMinT, String alphaT, String alphaP) {
		this.pMaxT = pMaxT;
		this.pMaxP = pMaxP;
		this.pMinP = pMinP;
		this.pMinT = pMinT;
		this.alphaT = alphaT;
		this.alphaP = alphaP;
	}

	public String getpMaxT() {
		return pMaxT;
	}

	public void setpMaxT(String pMaxT) {
		this.pMaxT = pMaxT;
	}

	public String getpMaxP() {
		return pMaxP;
	}

	public void setpMaxP(String pMaxP) {
		this.pMaxP = pMaxP;
	}

	public String getpMinP() {
		return pMinP;
	}

	public void setpMinP(String pMinP) {
		this.pMinP = pMinP;
	}

	public String getpMinT() {
		return pMinT;
	}

	public void setpMinT(String pMinT) {
		this.pMinT = pMinT;
	}

	public String getAlphaT() {
		return alphaT;
	}

	public void setAlphaT(String alphaT) {
		this.alphaT = alphaT;
	}

	public String getAlphaP() {
		return alphaP;
	}

	public void setAlphaP(String alphaP) {
		this.alphaP = alphaP;
	}
	
	
}
