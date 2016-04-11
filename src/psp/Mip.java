package psp;

import ilog.concert.*;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.List;import javax.swing.plaf.synth.SynthScrollBarUI;

public class Mip {
    private Instance instance;
    private IloCplex model;

    private boolean coutChangement = true;
    private boolean refroidissement = true;
    private boolean regulation = false;
    
    //Variable du PLNE
    private IloIntVar[][] isTurbine;
    private IloIntVar[][] isPompe;
    private IloNumVar[][] puissanceTurbine;
    private IloNumVar[][] puissancePompe;

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
        if(model.solve()){
        	  for (int i = 0; i < instance.getTPs().length; i++) {
                  for (int j = 0; j <instance.getCout().length; j++) {
					System.out.println("isPome"+(i+1)+"_"+(j+1)+" = " +model.getValue(this.isPompe[i][j]));
					System.out.println("isTurbine"+(i+1)+"_"+(j+1)+" = " +model.getValue(this.isTurbine[i][j]));
				}
				
			}
		}else{
		 System.out.println("elseeeeeeee");
		}
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
        this.isTurbine = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        this.isPompe = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        this.puissanceTurbine = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        this.puissancePompe = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 0; j <instance.getCout().length; j++) {
            	this.isTurbine[i][j] = model.boolVar("isTurbine"+i+"_"+j);
            	 this.isPompe[i][j] =  model.boolVar("isPompe"+i+"_"+j);
            	 this.puissanceTurbine[i][j] = model.numVar(0.0, Double.MAX_VALUE, "puissanceTurbine"+i+"_"+j);
            	 this.puissancePompe[i][j] = model.numVar(0.0, Double.MAX_VALUE, "puissancePompe"+i+"_"+j);
            }
        }
    }

    /**
     * Function initialisant les contraintes
     */
    private void initConstraints() throws IloException {
        initConstraintesPuissance();
//        initContraintesReservoir();
//        if (coutChangement)
//            initCoutChangementFonction();
//        if (refroidissement)
//            initConstraintsRefroidissmenet();
//        if (regulation)
//            initContraintesRegulation();
    }

    /**
     * Fonction initialisant les contraintes de puissances des turbines pompes
     */
    private void initConstraintesPuissance() throws IloException {

        TurbinePompe[] tPs = instance.getTPs();
        for (int i = 0; i < tPs.length; i++) {
            for (int j = 0; j < instance.getCout().length; j++) {
                contraintePuissanceTurbine(this.isTurbine[i][j], puissanceTurbine[i][j],i,j);
                contraintePuissancePompe(this.isPompe[i][j], puissancePompe[i][j],i,j);
                IloIntExpr contrainteActivationPompeTurbine = model.sum(this.isPompe[i][j], this.isTurbine[i][j]);
                model.addLe(contrainteActivationPompeTurbine, 1);
            }

        }

    }

    private void contraintePuissanceTurbine(IloIntVar turbinePompe, IloNumVar puissance, int i, int j) throws IloException {
        IloNumExpr expr1 = model.prod(turbinePompe, instance.getTPs()[i].getP_T_min());
        model.addLe(expr1, puissance);
        IloNumExpr expr2 = model.prod(turbinePompe, instance.getTPs()[i].getP_T_max());
        model.addLe(puissance, expr2, "contraintePuissance1TurbinePompe" + i +"_"+j);
    }
    private void contraintePuissancePompe(IloIntVar turbinePompe, IloNumVar puissance, int i, int j) throws IloException {
        IloNumExpr expr1 = model.prod(turbinePompe, instance.getTPs()[i].getP_P_max());
        model.addLe(expr1, puissance);
        IloNumExpr expr2 = model.prod(turbinePompe, instance.getTPs()[i].getP_P_min());
        model.addLe(puissance, expr2, "contraintePuissance1Pompe" + i +"_"+j);
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
        double[] cout = instance.getCout();
        IloNumExpr sum = model.intExpr();
        IloNumExpr coupTotal = model.intExpr();
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 0; j < cout.length; j++) {
                IloNumExpr coupTurbineActiver =  model.prod(isTurbine[i][j], cout[i], puissanceTurbine[i][j]);
                IloNumExpr coupPompeActiver = model.prod(isPompe[i][j], cout[i], puissancePompe[i][j]);
                coupTotal = model.sum(coupTurbineActiver, coupPompeActiver);
                sum = model.sum(sum,coupTotal);
            }
        }
        model.addMaximize(sum, "coutTotal");


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
