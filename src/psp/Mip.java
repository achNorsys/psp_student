package psp;


import java.io.File;

import ilog.concert.*;

import ilog.cplex.IloCplex;

public class Mip {
    private Instance instance;
    private IloCplex model;

    private boolean coutChangement = true;
    private boolean refroidissement = true;
    private boolean regulation = false;

    private IloIntVar[][] modeTurbine;
    private IloIntVar[][] modePompe;

    private IloNumVar[][] puissanceTurbine;
    private IloNumVar[][] puissancePompe;

    private IloNumVar[][] hauteurChute;

    private IloNumVar[][] coutAtoP;
    private IloNumVar[][] coutPtoA;
    private IloNumVar[][] coutAtoT;
    private IloNumVar[][] coutTtoA;
    IloNumExpr obj;

    /**
     * Constructeur d'un MIP pour résoudre l'instance
     */
    public Mip(Instance instance) throws IloException {
        this.instance = instance;
        initModel();
    }

    /**
     * Fonction résolvant l'instance.
     */
    public void solve() throws IloException {
        if (model.solve()) {
            System.out.println("Solution status = " + model.getStatus());
            System.out.println("Objective value : " + model.getObjValue());

            for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
                System.out.println("Machine numéro = : " + turbineCourante + "");
                for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                        System.out.println(" - ModePompe "+model.getValue(modePompe[turbineCourante][heureCourante]));
                        System.out.println(" - ModeTurbine " + model.getValue(modeTurbine[turbineCourante][heureCourante]));

                    System.out.println(" - puissanceTurbine" + model.getValue(puissanceTurbine[turbineCourante][heureCourante]));
                    System.out.println(" - puissancePompe" + model.getValue(puissancePompe[turbineCourante][heureCourante]));
                    System.out.println(" - hauteurChute" + model.getValue(hauteurChute[turbineCourante][heureCourante]));
                    System.out.println("----------------------------------------------------------------");
                }

            }
            for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
                System.out.println("Machine numéro = : " + turbineCourante + "");
                for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                    System.out.println("--------------------heure " + heureCourante + "------------------------");
//                    if (model.getValue(stateAtoP[turbineCourante][heureCourante]) == 1.0) {
//                        System.out.println(" - passage de A ver P");
//                    }
//                    if (model.getValue(statePtoA[turbineCourante][heureCourante]) == 1.0) {
//                        System.out.println(" - passage de P ver A");
//                    }
//                    if (model.getValue(stateAtoT[turbineCourante][heureCourante]) == 1.0) {
//                        System.out.println(" - passage de A ver T");
//                    }
//                    if (model.getValue(stateTtoA[turbineCourante][heureCourante]) == 1.0) {
//                        System.out.println(" - passage de T ver A");
//                    }
                    System.out.println("coutAtoP " + model.getValue(coutAtoP[turbineCourante][heureCourante]));
                    System.out.println("coutPtoA " + model.getValue(coutPtoA[turbineCourante][heureCourante]));
                    System.out.println("coutAtoT " + model.getValue(coutAtoT[turbineCourante][heureCourante]));
                    System.out.println("coutTtoA " + model.getValue(coutTtoA[turbineCourante][heureCourante]));
                    System.out.println("----------------------------------------------------------------");
                }
            }

        }
    }

    /**
     * Fonction retournant la valeur de l'objectif.
     * Requiert qu'une solution ait été trouvée
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
        Objective();
        initVariables();
        initConstraints();
        initObjective();
    }

    private void Objective() throws IloException {
        obj = model.intExpr();
    }

    /**
     * Function initialisant les variables
     */
    private void initVariables() throws IloException {
        modeTurbine = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        modePompe = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        puissanceTurbine = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        puissancePompe = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        hauteurChute = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        coutAtoP = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        coutPtoA = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        coutAtoT = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        coutTtoA = new IloNumVar[instance.getTPs().length][instance.getCout().length];
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 0; j < instance.getCout().length; j++) {
                modeTurbine[i][j] = model.boolVar("modeTurbine" + i + "_" + j);
                modePompe[i][j] = model.boolVar("modePompe" + i + "_" + j);
                coutAtoP[i][j] = model.numVar(-Double.MAX_VALUE, Double.MAX_VALUE, "stateAtoP" + i + "_" + j);
                coutPtoA[i][j] = model.numVar(-Double.MAX_VALUE, Double.MAX_VALUE, "statePtoA" + i + "_" + j);
                coutAtoT[i][j] = model.numVar(-Double.MAX_VALUE, Double.MAX_VALUE, "stateAtoT" + i + "_" + j);
                coutTtoA[i][j] = model.numVar(-Double.MAX_VALUE, Double.MAX_VALUE, "stateTtoA" + i + "_" + j);
                puissanceTurbine[i][j] = model.numVar(0, Double.MAX_VALUE, "puissanceTurbine" + i + "_" + j);
                puissancePompe[i][j] = model.numVar(-Double.MAX_VALUE, 0, "puissancePompe" + i + "_" + j);
                hauteurChute[i][j] = model.numVar(-instance.getInf().getHauteur() + instance.getDelta_H(), instance.getSup().getHauteur() + instance.getDelta_H(), "hauteurChute" + i + "_" + j);
            }
        }

    }

    /**
     * Function initialisant les contraintes
     */
    private void initConstraints() throws IloException {
        initConstraintesPuissance();
        initContraintesReservoir();
        if (isCoutChangement())
            initCoutChangementFonction();
        if (isRefroidissement())
            initConstraintsRefroidissmenet();
        if (isRegulation())
            initContraintesRegulation();
    }

    /**
     * Fonction initialisant les contraintes de puissances des turbines pompes
     */
    private void initConstraintesPuissance() throws IloException {
        for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
            for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                model.addLe(model.sum(modePompe[turbineCourante][heureCourante], modeTurbine[turbineCourante][heureCourante]), 1, "contrainteMode" + turbineCourante + "_" + heureCourante);
                model.addLe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getP_T_max()),
                        "contraintePuissanceTurbine" + turbineCourante + "_" + heureCourante);
                model.addGe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getP_T_min()),
                        "contraintePuissanceTurbine" + turbineCourante + "_" + heureCourante);
                model.addLe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getP_P_min()),
                        "contraintePuissancePompe" + turbineCourante + "_" + heureCourante);
                model.addGe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getP_P_max()),
                        "contraintePuissancePompe" + turbineCourante + "_" + heureCourante);
            }
        }
    }

    /**
     * Function initialisant les contraintes de reservoirs
     */
    private void initContraintesReservoir() throws IloException {
        for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
            model.addEq(hauteurChute[turbineCourante][0], instance.getSup().getH_0() - instance.getInf().getH_0() + instance.getDelta_H());
            for (int heureCourante = 0; heureCourante < instance.getCout().length-1; heureCourante++) {
                double coef = (2.0 * 3600.0) / (instance.getInf().getLargeur() * instance.getInf().getLongueur());
                IloNumExpr expr = model.prod(puissanceTurbine[turbineCourante][heureCourante+1], coef / instance.getTPs()[turbineCourante].getAlpha_P());
                expr = model.sum(expr, model.prod(puissancePompe[turbineCourante][heureCourante+1], coef / instance.getTPs()[turbineCourante].getAlpha_T()));
                model.addEq(model.diff(hauteurChute[turbineCourante][heureCourante], hauteurChute[turbineCourante][heureCourante+1]), expr);
            }
        }
    }

    /**
     * Fonction initialisant les couts de changement de fonctionnement
     */
    private void initCoutChangementFonction() throws IloException {

        for (int i = 0; i < instance.getTPs().length; i++) {
            IloNumExpr initAp = model.prod(modePompe[i][0], instance.getTPs()[i].getC_AP());
            model.addEq(coutAtoP[i][0], initAp);
            IloNumExpr initAt = model.prod(modeTurbine[i][0], instance.getTPs()[i].getC_AT());
            model.addEq(coutAtoT[i][0], initAt);
            model.addEq(coutPtoA[i][0], 0);
            model.addEq(coutTtoA[i][0], 0);
            for (int j = 1; j < instance.getCout().length; j++) {
                IloNumExpr coutAp = arretVersPompe(i, j);
                IloNumExpr coutPa = pompeVersArret(i, j);
                IloNumExpr coutAt = arretVersTurbine(i, j);
                IloNumExpr coutTa = turbineVersArret(i, j);
                IloNumExpr coutChangement = model.sum(coutAp, coutPa, coutAt, coutTa);
                obj = model.sum(obj, coutChangement);
            }
            obj = model.sum(obj,initAp,initAt);
        }
        System.out.println("Couts de changement de fonctionnement non implementees");
    }

    private IloNumExpr arretVersPompe(int i, int j) throws IloException {
        IloNumExpr initAp = model.prod(modePompe[i][j], instance.getTPs()[i].getC_AP());
        IloIntExpr diffAp = model.diff(1, modePompe[i][j - 1]);
        IloNumExpr coutAp = model.prod(initAp, diffAp);
        model.addEq(coutAtoP[i][j], coutAp);
        return coutAp;
    }

    //
    private IloNumExpr pompeVersArret(int i, int j) throws IloException {
        IloNumExpr initPa = model.prod(modePompe[i][j - 1], instance.getTPs()[i].getC_PA());
        IloIntExpr diffPa = model.diff(1, modePompe[i][j]);
        IloNumExpr coutPa = model.prod(initPa, diffPa);
        model.addEq(coutPtoA[i][j], coutPa);
        return coutPa;
    }

    private IloNumExpr arretVersTurbine(int i, int j) throws IloException {
        IloNumExpr initAt = model.prod(modeTurbine[i][j], instance.getTPs()[i].getC_AT());
        IloIntExpr diffAt = model.diff(1, modeTurbine[i][j - 1]);
        IloNumExpr coutAt = model.prod(initAt, diffAt);
        model.addEq(coutAtoT[i][j], coutAt);
        return coutAt;
    }

    private IloNumExpr turbineVersArret(int i, int j) throws IloException {
        IloNumExpr initTa = model.prod(modeTurbine[i][j - 1], instance.getTPs()[i].getC_TA());
        IloIntExpr diffTa = model.diff(1, modeTurbine[i][j]);
        IloNumExpr coutTa = model.prod(initTa, diffTa);
        model.addEq(coutTtoA[i][j], coutTa);
        return coutTa;
    }

    /**
     * Fonction initialisant les contraintes de refroidissement
     */
    private void initConstraintsRefroidissmenet() throws IloException {
        // TODO à vous de jouer
        System.out.println("Contraintes de refroidissement non implementees");
    }

    /**
     * Fonction initialisant les contraintes liees a la regulation
     */
    private void initContraintesRegulation() throws IloException {
        // TODO à vous de jouer
        System.out.println("Regulation non implementee");
    }

    /**
     * Fonction initialisant la fonction objectif
     */
    private void initObjective() throws IloException {

        for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
            for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                IloNumExpr coutPuissance = model.prod(instance.getCout()[heureCourante], model.sum(puissanceTurbine[turbineCourante][heureCourante], puissancePompe[turbineCourante][heureCourante]));
                obj = model.sum(obj, coutPuissance);
            }
        }

        model.addMaximize(obj, "objective");
        model.exportModel("Data" + File.separator + "lps" + File.separator + "exemple.lp");

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
