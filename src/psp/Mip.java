package psp;

import ilog.concert.*;
import ilog.cplex.IloCplex;

import java.util.ArrayList;
import java.util.List;

public class Mip {
    private Instance instance;
    private IloCplex model;

    private boolean coutChangement = true;
    private boolean refroidissement = true;
    private boolean regulation = false;
    private List<List<IloIntVar>> isTurbine;
    private List<List<IloIntVar>> isPompe;
    private List<List<IloNumVar>> puissanceTurbine;
    private List<List<IloNumVar>> puissancePompe;

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
        this.isTurbine = new ArrayList<>();
        this.isPompe = new ArrayList<>();
        this.puissanceTurbine = new ArrayList<>();
        this.puissancePompe = new ArrayList<>();
        List<IloIntVar> turbineInTime = new ArrayList<>();
        List<IloIntVar> pompeInTime = new ArrayList<>();
        List<IloNumVar> puissanceTurbineInTime = new ArrayList<>();
        List<IloNumVar> puissancePompeInTime = new ArrayList<>();
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 0; j <instance.getCout().length; j++) {
                turbineInTime.add(model.boolVar("isTurbine"+i+""+j));
                pompeInTime.add(model.boolVar("isPompe"+i+""+j));
                puissanceTurbineInTime.add(model.numVar(0.0, Double.MAX_VALUE, "puissanceTurbine"+i+""+j));
                puissancePompeInTime.add(model.numVar(0.0, Double.MAX_VALUE, "puissancePompe"+i+""+j));
            }
            this.isTurbine.add(turbineInTime);
            this.isPompe.add(pompeInTime);
            this.puissanceTurbine.add(puissanceTurbineInTime);
            this.puissancePompe.add(puissancePompeInTime);
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
            // Contrainte 1
            List<IloIntVar> turbine = isTurbine.get(i);
            List<IloIntVar> pompe = isPompe.get(i);
            for (int j = 0; j < instance.getCout().length; j++) {
                List<IloNumVar> puissanceTurbine = this.puissanceTurbine.get(i);
                List<IloNumVar> puissancePompe = this.puissancePompe.get(i);
                contraintePuissance(turbine.get(j), puissanceTurbine.get(j),i,j);
                contraintePuissance(pompe.get(j), puissancePompe.get(j),i,j);
                IloIntExpr contrainteActivationPompeTurbine = model.sum(pompe.get(j), turbine.get(j));
                model.addLe(contrainteActivationPompeTurbine, 1);
            }

        }

    }

    private void contraintePuissance(IloIntVar turbinePompe, IloNumVar puissance, int i, int j) throws IloException {
        IloNumExpr expr1 = model.prod(turbinePompe, instance.getTPs()[i].getP_T_min());
        IloNumExpr expr3 = model.le(expr1, puissance);
        IloNumExpr expr2 = model.prod(turbinePompe, instance.getTPs()[i].getP_T_max());
        model.addLe(expr3, expr2, "contraintePuissance1TurbinePompe" + i +"_"+j);
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
                IloNumExpr coupTurbineActiver =  model.prod(isTurbine.get(i).get(j), cout[i], puissanceTurbine.get(i).get(j));
                IloNumExpr coupPompeActiver = model.prod(isPompe.get(i).get(j), cout[i], puissancePompe.get(i).get(j));
                coupTotal = model.sum(coupTurbineActiver, coupPompeActiver);
            }
            sum = model.sum(sum,coupTotal);
        }


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
