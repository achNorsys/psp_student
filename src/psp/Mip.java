package psp;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;

import ilog.concert.*;
import org.junit.Assert;

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

    private IloIntVar[][] stateAtoP;
    private IloIntVar[][] statePtoA;
    private IloIntVar[][] stateAtoT;
    private IloIntVar[][] stateTtoA;

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
                    if (model.getValue(modePompe[turbineCourante][heureCourante]) == 1.0) {
                        System.out.println(" - ModePompe");
                    } else if (model.getValue(modeTurbine[turbineCourante][heureCourante]) == 1.0) {
                        System.out.println(" - ModeTurbine");
                    } else {
                        System.out.println(" - ModeArret");
                    }
                    System.out.println(" - puissanceTurbine" + model.getValue(puissanceTurbine[turbineCourante][heureCourante]));
                    System.out.println(" - puissancePompe" + model.getValue(puissancePompe[turbineCourante][heureCourante]));
                    System.out.println(" - hauteurChute" + model.getValue(hauteurChute[turbineCourante][heureCourante]));
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
        //Objective();
        initVariables();
        initConstraints();
        initObjective();
    }

    private void Objective() throws IloException {

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
        stateAtoP = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        statePtoA = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        stateAtoT = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        stateTtoA = new IloIntVar[instance.getTPs().length][instance.getCout().length];
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 0; j < instance.getCout().length; j++) {
                modeTurbine[i][j] = model.boolVar("modeTurbine" + i + "_" + j);
                modePompe[i][j] = model.boolVar("modePompe" + i + "_" + j);
                stateAtoP[i][j] = model.boolVar("stateAtoP" + i + "_" + j);
                statePtoA[i][j] = model.boolVar("statePtoA" + i + "_" + j);
                stateAtoT[i][j] = model.boolVar("stateAtoT" + i + "_" + j);
                stateTtoA[i][j] = model.boolVar("stateTtoA" + i + "_" + j);
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
        for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
            for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                model.addLe(model.sum(modePompe[turbineCourante][heureCourante], modeTurbine[turbineCourante][heureCourante]), 1, "contrainteMode" + turbineCourante + "_" + heureCourante);
                model.addLe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[0].getP_T_max()),
                        "contraintePuissanceTurbine" + turbineCourante + "_" + heureCourante);
                model.addGe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[0].getP_T_min()),
                        "contraintePuissanceTurbine" + turbineCourante + "_" + heureCourante);
                model.addLe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[0].getP_P_min()),
                        "contraintePuissancePompe" + turbineCourante + "_" + heureCourante);
                model.addGe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[0].getP_P_max()),
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
            Double coef = 2.0 * 3600.0 / (instance.getInf().getLargeur() * instance.getInf().getLongueur());
            IloNumExpr expr = model.prod(puissanceTurbine[turbineCourante][0], coef / instance.getTPs()[0].getAlpha_P());
            expr = model.sum(expr, model.prod(puissancePompe[turbineCourante][0], coef / instance.getTPs()[0].getAlpha_T()));
            model.addEq(model.diff(hauteurChute[turbineCourante][0], hauteurChute[turbineCourante][0]), expr);
            for (int heureCourante = 1; heureCourante < instance.getCout().length ; heureCourante++) {
                 coef = 2.0 * 3600.0 / (instance.getInf().getLargeur() * instance.getInf().getLongueur());
                 expr = model.prod(puissanceTurbine[turbineCourante][heureCourante], coef / instance.getTPs()[0].getAlpha_P());
                expr = model.sum(expr, model.prod(puissancePompe[turbineCourante][heureCourante], coef / instance.getTPs()[0].getAlpha_T()));
                model.addEq(model.diff(hauteurChute[turbineCourante][heureCourante-1], hauteurChute[turbineCourante][heureCourante]), expr);
            }
        }
    }

    /**
     * Fonction initialisant les couts de changement de fonctionnement
     */
    private void initCoutChangementFonction() throws IloException {
        // TODO à vous de jouer
        for (int i = 0; i < instance.getTPs().length; i++) {
            for (int j = 1; j < instance.getCout().length; j++) {
                arretVersPompe(i, j);
                pompeVersArret(i, j);
                arretVersTurbine(i, j);
                turbineVersArret(i, j);
            }
        }

        System.out.println("Couts de changement de fonctionnement non implementees");
    }

    private void arretVersPompe(int i, int j) throws IloException {
        IloRange eq = model.eq(modePompe[i][j], 1.0);
        IloRange eq1 = model.eq(modePompe[i][j - 1], 0.0);
        IloRange eq2 = model.eq(modeTurbine[i][j - 1], 0.0);
        model.addEq(stateAtoP[i][j], model.eq(model.eq(eq, eq1), eq2));
    }

    private void pompeVersArret(int i, int j) throws IloException {
        IloRange eq = model.eq(modePompe[i][j - 1], 1.0);
        IloRange eq1 = model.eq(modePompe[i][j], 0.0);
        IloRange eq2 = model.eq(modeTurbine[i][j], 0.0);
        model.addEq(stateAtoP[i][j], model.eq(model.eq(eq, eq1), eq2));
    }

    private void arretVersTurbine(int i, int j) throws IloException {
        IloRange eq = model.eq(modeTurbine[i][j], 1.0);
        IloRange eq1 = model.eq(modePompe[i][j - 1], 0.0);
        IloRange eq2 = model.eq(modeTurbine[i][j - 1], 0.0);
        model.addEq(stateAtoP[i][j], model.eq(model.eq(eq, eq1), eq2));
    }

    private void turbineVersArret(int i, int j) throws IloException {
        IloRange eq = model.eq(modeTurbine[i][j-1], 1.0);
        IloRange eq1 = model.eq(modePompe[i][j], 0.0);
        IloRange eq2 = model.eq(modeTurbine[i][j], 0.0);
        model.addEq(stateAtoP[i][j], model.eq(model.eq(eq, eq1), eq2));
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

        IloNumExpr obj = model.intExpr();

        for (int turbineCourante = 0; turbineCourante < instance.getTPs().length; turbineCourante++) {
            for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                IloNumExpr coutAp = model.prod(stateAtoP[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getC_AP());
                IloNumExpr coutPA = model.prod(statePtoA[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getC_PA());
                IloNumExpr coutAT = model.prod(stateAtoT[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getC_AT());
                IloNumExpr coutTA = model.prod(stateTtoA[turbineCourante][heureCourante], instance.getTPs()[turbineCourante].getC_TA());
                IloNumExpr coutChangement = model.sum(coutAp, coutPA, coutAT, coutTA);
                obj = model.sum(obj,coutChangement);
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
