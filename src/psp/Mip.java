package psp;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class Mip {
	private Instance instance;
	private IloCplex model;
	
	private boolean coutChangement = true;
	private boolean refroidissement = true;
	private boolean regulation = false;
	private IloIntVar isTurbine;
	private IloIntVar isPompe;
	private IloNumVar puissanceTurbine;
	private IloNumVar puissancePompe;
	
	/**
	 * Constructeur d'un MIP pour r�soudre l'instance
	 */
	public Mip(Instance instance) throws IloException {
		this.instance = instance;
		initModel();
	}

	/**
	 * Fonction résolvant l'instance.
	 */
	public void solve() throws IloException {
		model.solve();
	}

	/**
	 * Fonction retournant la valeur de l'objectif.
	 * Requiert qu'une solution ait �t� trouv�e
	 */
	public double getObjValue() throws IloException {
		return model.getObjValue();
	}
	
	/**
	 * Fonction liberant la memoire utilisee par le model
	 */
	public void clear() {
		model.end();
	}
	
	/**
	 * Fonction initialisant le model Cplex
	 */
	private void initModel() throws IloException {
		model = new IloCplex();
		initVariables();
		initConstraints();
		initObjective();
	}

	/**
	 * Function initialisant les variables
	 */
	private void initVariables() throws IloException {
		isTurbine = model.boolVar("isTurbine");
		isPompe = model.boolVar("isPompe");
		puissanceTurbine = model.numVar(0.0, Double.MAX_VALUE, "puissanceTurbine");
		puissancePompe = model.numVar(0.0, Double.MAX_VALUE, "puissancePompe");
	}
	
	/**
	 * Function initialisant les contraintes
	 */
	private void initConstraints() throws IloException {
		initConstraintesPuissance();
		initContraintesReservoir();
		if (coutChangement)
			initCoutChangementFonction();
		if (refroidissement)
			initConstraintsRefroidissmenet();
		if (regulation)
			initContraintesRegulation();
	}
	
	/**
	 * Fonction initialisant les contraintes de puissances des turbines pompes
	 */
	private void initConstraintesPuissance() throws IloException {
		// Contrainte 1
		TurbinePompe[] tPs = instance.getTPs();
		for (int i = 0; i < tPs.length; i++) {
			IloNumExpr expr1 = model.prod(isTurbine,tPs[i].getP_T_min());
			//IloNumExpr expr2 = model.le(expr1,tPs[i].)
			model.addLe(expr1, 150000.0, "ctr_1");
		}
	}
	
	/**
	 * Function initialisant les contraintes de reservoirs
	 */
	private void initContraintesReservoir() throws IloException {
		// TODO � vous de jouer
		System.out.println("Contraintes de reservoir non implementees");
		System.exit(1);
	}

	/**
	 * Fonction initialisant les couts de changement de fonctionnement
	 */
	private void initCoutChangementFonction() throws IloException {
		// TODO � vous de jouer
		System.out.println("Couts de changement de fonctionnement non implementees");
		System.exit(1);
	}

	/**
	 * Fonction initialisant les contraintes de refroidissement
	 */
	private void initConstraintsRefroidissmenet() throws IloException {
		// TODO � vous de jouer
		System.out.println("Contraintes de refroidissement non implementees");
		System.exit(1);
	}

	/**
	 * Fonction initialisant les contraintes liees a la regulation
	 */
	private void initContraintesRegulation() throws IloException {
		// TODO � vous de jouer
		System.out.println("Regulation non implementee");
		System.exit(1);
	}
	
	/**
	 * Fonction initialisant la fonction objectif 
	 */
	private void initObjective() throws IloException {
		// TODO � vous de jouer
		System.out.println("Objectif non implemente");
		System.exit(1);
	}

	public boolean isCoutChangement() {
		return coutChangement;
	}

	public void setCoutChangement(boolean coutChangement) {
		this.coutChangement = coutChangement;
	}

	public boolean isRefroidissement() {
		return refroidissement;
	}

	public void setRefroidissement(boolean refroidissement) {
		this.refroidissement = refroidissement;
	}

	public boolean isRegulation() {
		return regulation;
	}

	public void setRegulation(boolean regulation) {
		this.regulation = regulation;
	}
}
