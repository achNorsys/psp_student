\ENCODING=ISO-8859-1
\Problem name: ilog.cplex

Maximize
 obj: - 30 modePompe0_0 - 10 modeTurbine0_0 - 50 modePompe0_1
      - 11 modeTurbine0_1 - 50 modePompe0_2 - 11 modeTurbine0_2
      - 50 modePompe0_3 - 11 modeTurbine0_3 + 12 puissanceTurbine0_3
      + 12 puissancePompe0_3 - 20 modePompe0_4 - modeTurbine0_4
      + 12 puissanceTurbine0_4 + 12 puissancePompe0_4 + 0 stateAtoP0_1
      + 0 statePtoA0_1 + 0 stateAtoT0_1 + 0 stateTtoA0_1 + 0 stateAtoP0_2
      + 0 statePtoA0_2 + 0 stateAtoT0_2 + 0 stateTtoA0_2 + 0 stateAtoP0_3
      + 0 statePtoA0_3 + 0 stateAtoT0_3 + 0 stateTtoA0_3 + 0 stateAtoP0_4
      + 0 statePtoA0_4 + 0 stateAtoT0_4 + 0 stateTtoA0_4
      + [ 100 modePompe0_0 * modePompe0_1 + 22 modeTurbine0_0 * modeTurbine0_1
      + 100 modePompe0_1 * modePompe0_2 + 22 modeTurbine0_1 * modeTurbine0_2
      + 100 modePompe0_2 * modePompe0_3 + 22 modeTurbine0_2 * modeTurbine0_3
      + 100 modePompe0_3 * modePompe0_4 + 22 modeTurbine0_3 * modeTurbine0_4
      ] / 2
Subject To
 contrainteMode0_0:             modePompe0_0 + modeTurbine0_0 <= 1
 contraintePuissanceTurbine0_0: - 300 modeTurbine0_0 + puissanceTurbine0_0 <= 0
 contraintePuissanceTurbine0_0: - 200 modeTurbine0_0 + puissanceTurbine0_0 >= 0
 contraintePuissancePompe0_0:   150 modePompe0_0 + puissancePompe0_0 <= 0
 contraintePuissancePompe0_0:   200 modePompe0_0 + puissancePompe0_0 >= 0
 contrainteMode0_1:             modePompe0_1 + modeTurbine0_1 <= 1
 contraintePuissanceTurbine0_1: - 300 modeTurbine0_1 + puissanceTurbine0_1 <= 0
 contraintePuissanceTurbine0_1: - 200 modeTurbine0_1 + puissanceTurbine0_1 >= 0
 contraintePuissancePompe0_1:   150 modePompe0_1 + puissancePompe0_1 <= 0
 contraintePuissancePompe0_1:   200 modePompe0_1 + puissancePompe0_1 >= 0
 contrainteMode0_2:             modePompe0_2 + modeTurbine0_2 <= 1
 contraintePuissanceTurbine0_2: - 300 modeTurbine0_2 + puissanceTurbine0_2 <= 0
 contraintePuissanceTurbine0_2: - 200 modeTurbine0_2 + puissanceTurbine0_2 >= 0
 contraintePuissancePompe0_2:   150 modePompe0_2 + puissancePompe0_2 <= 0
 contraintePuissancePompe0_2:   200 modePompe0_2 + puissancePompe0_2 >= 0
 contrainteMode0_3:             modePompe0_3 + modeTurbine0_3 <= 1
 contraintePuissanceTurbine0_3: - 300 modeTurbine0_3 + puissanceTurbine0_3 <= 0
 contraintePuissanceTurbine0_3: - 200 modeTurbine0_3 + puissanceTurbine0_3 >= 0
 contraintePuissancePompe0_3:   150 modePompe0_3 + puissancePompe0_3 <= 0
 contraintePuissancePompe0_3:   200 modePompe0_3 + puissancePompe0_3 >= 0
 contrainteMode0_4:             modePompe0_4 + modeTurbine0_4 <= 1
 contraintePuissanceTurbine0_4: - 300 modeTurbine0_4 + puissanceTurbine0_4 <= 0
 contraintePuissanceTurbine0_4: - 200 modeTurbine0_4 + puissanceTurbine0_4 >= 0
 contraintePuissancePompe0_4:   150 modePompe0_4 + puissancePompe0_4 <= 0
 contraintePuissancePompe0_4:   200 modePompe0_4 + puissancePompe0_4 >= 0
 c26:                           hauteurChute0_0  = 400
 c27:                           - 0.00132244897959184 puissanceTurbine0_1
                                - 0.00163265306122449 puissancePompe0_1
                                + hauteurChute0_0 - hauteurChute0_1  = 0
 c28:                           - 0.00132244897959184 puissanceTurbine0_2
                                - 0.00163265306122449 puissancePompe0_2
                                + hauteurChute0_1 - hauteurChute0_2  = 0
 c29:                           - 0.00132244897959184 puissanceTurbine0_3
                                - 0.00163265306122449 puissancePompe0_3
                                + hauteurChute0_2 - hauteurChute0_3  = 0
 c30:                           - 0.00132244897959184 puissanceTurbine0_4
                                - 0.00163265306122449 puissancePompe0_4
                                + hauteurChute0_3 - hauteurChute0_4  = 0
 q1:  stateAtoP0_1 + 20 modePompe0_1 + [ - 20 modePompe0_0 * modePompe0_1 ]
       = 0
 q2:  statePtoA0_1 + 30 modePompe0_0 + [ - 30 modePompe0_0 * modePompe0_1 ]
       = 0
 q3:  stateAtoT0_1 + modeTurbine0_1 + [ - modeTurbine0_0 * modeTurbine0_1 ]
       = 0
 q4:  stateTtoA0_1 + 10 modeTurbine0_0
      + [ - 10 modeTurbine0_0 * modeTurbine0_1 ]  = 0
 q5:  stateAtoP0_2 + 20 modePompe0_2 + [ - 20 modePompe0_1 * modePompe0_2 ]
       = 0
 q6:  statePtoA0_2 + 30 modePompe0_1 + [ - 30 modePompe0_1 * modePompe0_2 ]
       = 0
 q7:  stateAtoT0_2 + modeTurbine0_2 + [ - modeTurbine0_1 * modeTurbine0_2 ]
       = 0
 q8:  stateTtoA0_2 + 10 modeTurbine0_1
      + [ - 10 modeTurbine0_1 * modeTurbine0_2 ]  = 0
 q9:  stateAtoP0_3 + 20 modePompe0_3 + [ - 20 modePompe0_2 * modePompe0_3 ]
       = 0
 q10: statePtoA0_3 + 30 modePompe0_2 + [ - 30 modePompe0_2 * modePompe0_3 ]
       = 0
 q11: stateAtoT0_3 + modeTurbine0_3 + [ - modeTurbine0_2 * modeTurbine0_3 ]
       = 0
 q12: stateTtoA0_3 + 10 modeTurbine0_2
      + [ - 10 modeTurbine0_2 * modeTurbine0_3 ]  = 0
 q13: stateAtoP0_4 + 20 modePompe0_4 + [ - 20 modePompe0_3 * modePompe0_4 ]
       = 0
 q14: statePtoA0_4 + 30 modePompe0_3 + [ - 30 modePompe0_3 * modePompe0_4 ]
       = 0
 q15: stateAtoT0_4 + modeTurbine0_4 + [ - modeTurbine0_3 * modeTurbine0_4 ]
       = 0
 q16: stateTtoA0_4 + 10 modeTurbine0_3
      + [ - 10 modeTurbine0_3 * modeTurbine0_4 ]  = 0
Bounds
 0 <= modePompe0_0 <= 1
 0 <= modeTurbine0_0 <= 1
 -Inf <= puissancePompe0_0 <= 0
 0 <= modePompe0_1 <= 1
 0 <= modeTurbine0_1 <= 1
 -Inf <= puissancePompe0_1 <= 0
 0 <= modePompe0_2 <= 1
 0 <= modeTurbine0_2 <= 1
 -Inf <= puissancePompe0_2 <= 0
 0 <= modePompe0_3 <= 1
 0 <= modeTurbine0_3 <= 1
 -Inf <= puissancePompe0_3 <= 0
 0 <= modePompe0_4 <= 1
 0 <= modeTurbine0_4 <= 1
 -Inf <= puissancePompe0_4 <= 0
 400 <= hauteurChute0_0 <= 600
 400 <= hauteurChute0_1 <= 600
 400 <= hauteurChute0_2 <= 600
 400 <= hauteurChute0_3 <= 600
 400 <= hauteurChute0_4 <= 600
      stateAtoP0_1 Free
      statePtoA0_1 Free
      stateAtoT0_1 Free
      stateTtoA0_1 Free
      stateAtoP0_2 Free
      statePtoA0_2 Free
      stateAtoT0_2 Free
      stateTtoA0_2 Free
      stateAtoP0_3 Free
      statePtoA0_3 Free
      stateAtoT0_3 Free
      stateTtoA0_3 Free
      stateAtoP0_4 Free
      statePtoA0_4 Free
      stateAtoT0_4 Free
      stateTtoA0_4 Free
Binaries
 modePompe0_0  modeTurbine0_0  modePompe0_1  modeTurbine0_1  modePompe0_2 
 modeTurbine0_2  modePompe0_3  modeTurbine0_3  modePompe0_4  modeTurbine0_4 
End
