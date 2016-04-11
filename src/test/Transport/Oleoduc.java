package test.Transport;

import ilog.concert.IloException;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.io.File;

import static org.junit.Assert.fail;

public class Oleoduc {
	public static void main(String[] args) throws IloException {
		IloCplex modelCour = new IloCplex();
		modelCour.setName("Exemple");
		// variables
		IloNumVar xO1a = modelCour.numVar(0.0, Double.MAX_VALUE, "xO1a");
		IloNumVar xO1b = modelCour.numVar(0.0, Double.MAX_VALUE, "xO1b");
		IloNumVar xO2a = modelCour.numVar(0.0, Double.MAX_VALUE, "xO2a");
		IloNumVar xO2b = modelCour.numVar(0.0, Double.MAX_VALUE, "xO2b");
		IloNumVar xOba = modelCour.numVar(0.0, Double.MAX_VALUE, "xOba");

		IloNumVar yO1a = modelCour.boolVar("yO1a");
		IloNumVar yO1b = modelCour.boolVar("yO1b");
		IloNumVar yO2a = modelCour.boolVar("yO2a");
		IloNumVar yO2b = modelCour.boolVar("yO2b");
		IloNumVar yOba = modelCour.boolVar("yOba");

		// fonction objectif
		IloNumExpr coupTransportTotal = modelCour.sum(modelCour.prod(4.0, xO1a),modelCour.prod(1.0, xO1b),
				modelCour.prod(2.0, xO2a),modelCour.prod(3.0, xO2b),modelCour.prod(0.0, xOba));
		IloNumExpr coupLocationTotal = modelCour.sum(modelCour.prod(130000.0, yO1a),modelCour.prod(90000.0, yO1b),
				modelCour.prod(80000.0, yO2a),modelCour.prod(140000.0, yO2b),modelCour.prod(150000.0, yOba));
		IloNumExpr coupTotal = modelCour.sum(coupTransportTotal,coupLocationTotal);

		modelCour.addMinimize(coupTotal, "coutTotal");

		// Contrainte 1
		IloNumExpr expr1 = modelCour.sum(xO1a,xO2a,xOba);
		modelCour.addLe(expr1, 150000.0, "ctr_1");

		// Contrainte 2
		IloNumExpr expr2 = modelCour.sum(xO1b,xO2b,modelCour.prod(-1,xOba));
		modelCour.addLe(expr2, 50000.0, "ctr_2");

		// Contrainte 3
		IloNumExpr expr3 = modelCour.sum(xO1a,xO1b);
		modelCour.addEq(expr3,75000.0, "ctr_3");

		// Contrainte 4
		IloNumExpr expr4 = modelCour.sum(xO2b,xO2a);
		modelCour.addEq(expr4,75000.0, "ctr_4");

		// Contrainte 5
		IloNumExpr expr5 = modelCour.prod(70000.0,yO1a);
		modelCour.addLe(xO1a,expr5, "ctr_5");
		// Contrainte 6
		IloNumExpr expr6 = modelCour.prod(30000.0,yO2a);
		modelCour.addLe(xO2a,expr6, "ctr_6");
		// Contrainte 7
		IloNumExpr expr7 = modelCour.prod(40000.0,yO1b);
		modelCour.addLe(xO1b,expr7, "ctr_7");
		// Contrainte 7
		IloNumExpr expr8 = modelCour.prod(80000.0,yO2b);
		modelCour.addLe(xO2b,expr8, "ctr_8");
		// Contrainte 7
		IloNumExpr expr9 = modelCour.prod(100000.0,yOba);
		modelCour.addLe(xOba,expr9, "ctr_9");

		modelCour.exportModel("Data"+ File.separator+"lps"+File.separator+"exemple.lp");
		if (modelCour.solve()) {
			System.out.println("\nSolution status = " + modelCour.getStatus());
			System.out.println("Objective value : " + modelCour.getObjValue());
			System.out.println("Solution : ");
			System.out.println("\txO1a = "+ modelCour.getValue(xO1a));
			System.out.println("\txO1b = "+ modelCour.getValue(xO1b));
			System.out.println("\txO2a = "+ modelCour.getValue(xO2a));
			System.out.println("\txO2b = "+ modelCour.getValue(xO2b));
			System.out.println("\txOba = "+ modelCour.getValue(xOba));
			System.out.println("\tyO1a = "+ modelCour.getValue(yO1a));
			System.out.println("\tyO1b = "+ modelCour.getValue(yO1b));
			System.out.println("\tyO2a = "+ modelCour.getValue(yO2a));
			System.out.println("\tyO2b = "+ modelCour.getValue(yO2b));
			System.out.println("\tyOba = "+ modelCour.getValue(yOba));
		} else {
			fail("No feaisible solution has been found");
		}
	}
}
