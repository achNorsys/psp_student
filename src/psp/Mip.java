package psp;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;

import org.junit.Assert;

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

    private IloIntVar[][] modeTurbine;
    private IloIntVar[][] modePompe;

    private IloNumVar[][] puissanceTurbine;
    private IloNumVar[][] puissancePompe;

    private IloNumVar[][] hauteurChute;

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
        if(model.solve()){
            System.out.println("Solution status = " + model.getStatus());
            System.out.println("Objective value : " + model.getObjValue());

            for(int turbineCourante = 0; turbineCourante< instance.getTPs().length; turbineCourante++) {
                System.out.println("Machine numéro = : " + turbineCourante + "");
                for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                    if (model.getValue(modePompe[turbineCourante][heureCourante]) == 1.0) {
                        System.out.println(" - ModePompe");
                    } else if (model.getValue(modeTurbine[turbineCourante][heureCourante]) == 1.0) {
                        System.out.println(" - ModeTurbine");
                    } else {
                        System.out.println(" - ModeArret");
                    }
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

    private void Objective() throws IloException{

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
        for(int i = 0; i< instance.getTPs().length; i++) {
            for (int j = 0; j < instance.getCout().length; j++) {
                modeTurbine[i][j] = model.boolVar("modeTurbine"+i+"_"+j);
                modePompe[i][j] = model.boolVar("modePompe"+i+"_"+j);
                puissanceTurbine[i][j] = model.numVar(0, Double.MAX_VALUE,"puissanceTurbine"+i+"_"+j);
                puissancePompe[i][j] = model.numVar(-Double.MAX_VALUE, 0,"puissancePompe"+i+"_"+j);
                hauteurChute[i][j] = model.numVar(-instance.getInf().getHauteur() + instance.getDelta_H(), instance.getSup().getHauteur() + instance.getDelta_H(),"hauteurChute"+i+"_"+j);
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
        for(int turbineCourante = 0; turbineCourante< instance.getTPs().length; turbineCourante++) {
            for (int heureCourante = 0; heureCourante < instance.getCout().length; heureCourante++) {
                model.addLe(model.sum(modePompe[turbineCourante][heureCourante], modeTurbine[turbineCourante][heureCourante]), 1,"contrainteMode"+turbineCourante+"_" + heureCourante);
                model.addLe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[0].getP_T_max()),
                        "contraintePuissanceTurbine"+turbineCourante+"_" + heureCourante);
                model.addGe(puissanceTurbine[turbineCourante][heureCourante], model.prod(modeTurbine[turbineCourante][heureCourante], instance.getTPs()[0].getP_T_min()),
                        "contraintePuissanceTurbine"+turbineCourante+"_" + heureCourante);
                model.addLe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[0].getP_P_min()),
                        "contraintePuissancePompe"+turbineCourante+"_" + heureCourante);
                model.addGe(puissancePompe[turbineCourante][heureCourante], model.prod(modePompe[turbineCourante][heureCourante], instance.getTPs()[0].getP_P_max()),
                        "contraintePuissancePompe"+turbineCourante+"_" + heureCourante);
            }
        }
    }

    /**
     * Function initialisant les contraintes de reservoirs
     */
    private void initContraintesReservoir() throws IloException {
        for(int turbineCourante=0; turbineCourante<instance.getTPs().length; turbineCourante++) {
            model.addEq(hauteurChute[turbineCourante][0], instance.getSup().getH_0() - instance.getInf().getH_0() + instance.getDelta_H());
            for (int heureCourante = 1; heureCourante < instance.getCout().length ; heureCourante++) {
                Double coef = 2 * 3600 / (instance.getInf().getLargeur() * instance.getInf().getLongueur());
                IloNumExpr expr = model.prod(puissancePompe[turbineCourante][heureCourante-1], coef / instance.getTPs()[0].getAlpha_P());
                expr = model.sum(expr, model.prod(puissanceTurbine[turbineCourante][heureCourante-1], coef / instance.getTPs()[0].getAlpha_T()));

                model.addEq(model.diff(hauteurChute[turbineCourante][heureCourante-1], hauteurChute[turbineCourante][heureCourante]), expr);
            }
        }
    }

    /**
     * Fonction initialisant les couts de changement de fonctionnement
     */
    private void initCoutChangementFonction() throws IloException {
        // TODO à vous de jouer
        System.out.println("Couts de changement de fonctionnement non implementees");
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

        for(int turbineCourante=0; turbineCourante<instance.getTPs().length; turbineCourante++){
            for(int heureCourante=0; heureCourante<instance.getCout().length; heureCourante++) {
                obj = model.sum(obj, model.prod(instance.getCout()[heureCourante], model.sum(puissanceTurbine[turbineCourante][heureCourante], puissancePompe[turbineCourante][heureCourante])));
            }
        }

        model.addMaximize(obj, "objective");
        model.exportModel("Data"+File.separator+"lps"+File.separator+"exemple.lp");

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
