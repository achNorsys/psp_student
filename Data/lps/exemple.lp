\ENCODING=ISO-8859-1
\Problem name: ilog.cplex

Maximize
 obj: - 100999 modePompe0_0 - 39999 modeTurbine0_0 - 202399 modePompe0_1
      - 59998 modeTurbine0_1 - 101400 modePompe0_2 - 19999 modeTurbine0_2
      + 1555 puissanceTurbine0_2 + 1555 puissancePompe0_2 + 0 stateAtoP0_1
      + 0 statePtoA0_1 + 0 stateAtoT0_1 + 0 stateTtoA0_1 + 0 stateAtoP0_2
      + 0 statePtoA0_2 + 0 stateAtoT0_2 + 0 stateTtoA0_2
      + [ 404798 modePompe0_0 * modePompe0_1
      + 119996 modeTurbine0_0 * modeTurbine0_1
      + 404798 modePompe0_1 * modePompe0_2
      + 119996 modeTurbine0_1 * modeTurbine0_2 ] / 2
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
 c16:                           hauteurChute0_0  = 400
 c17:                           - 0.00132244897959184 puissanceTurbine0_0
                                - 0.00163265306122449 puissancePompe0_0  = 0
 c18:                           - 0.00132244897959184 puissanceTurbine0_1
                                - 0.00163265306122449 puissancePompe0_1
                                + hauteurChute0_0 - hauteurChute0_1  = 0
 c19:                           - 0.00132244897959184 puissanceTurbine0_2
                                - 0.00163265306122449 puissancePompe0_2
                                + hauteurChute0_1 - hauteurChute0_2  = 0
 q1: stateAtoP0_1 + 101400 modePompe0_1
     + [ - 101400 modePompe0_0 * modePompe0_1 ]  = 0
 q2: statePtoA0_1 + 100999 modePompe0_0
     + [ - 100999 modePompe0_0 * modePompe0_1 ]  = 0
 q3: stateAtoT0_1 + 19999 modeTurbine0_1
     + [ - 19999 modeTurbine0_0 * modeTurbine0_1 ]  = 0
 q4: stateTtoA0_1 + 39999 modeTurbine0_0
     + [ - 39999 modeTurbine0_0 * modeTurbine0_1 ]  = 0
 q5: stateAtoP0_2 + 101400 modePompe0_2
     + [ - 101400 modePompe0_1 * modePompe0_2 ]  = 0
 q6: statePtoA0_2 + 100999 modePompe0_1
     + [ - 100999 modePompe0_1 * modePompe0_2 ]  = 0
 q7: stateAtoT0_2 + 19999 modeTurbine0_2
     + [ - 19999 modeTurbine0_1 * modeTurbine0_2 ]  = 0
 q8: stateTtoA0_2 + 39999 modeTurbine0_1
     + [ - 39999 modeTurbine0_1 * modeTurbine0_2 ]  = 0
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
 400 <= hauteurChute0_0 <= 600
 400 <= hauteurChute0_1 <= 600
 400 <= hauteurChute0_2 <= 600
      stateAtoP0_1 Free
      statePtoA0_1 Free
      stateAtoT0_1 Free
      stateTtoA0_1 Free
      stateAtoP0_2 Free
      statePtoA0_2 Free
      stateAtoT0_2 Free
      stateTtoA0_2 Free
Binaries
 modePompe0_0  modeTurbine0_0  modePompe0_1  modeTurbine0_1  modePompe0_2 
 modeTurbine0_2 
End
