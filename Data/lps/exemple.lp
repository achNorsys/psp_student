\ENCODING=ISO-8859-1
\Problem name: ilog.cplex

Maximize
 obj: 214 puissanceTurbine0_0 + 214 puissancePompe0_0 + 25 puissanceTurbine0_1
      + 25 puissancePompe0_1 + 2 puissanceTurbine0_2 + 2 puissancePompe0_2
      + 2 puissanceTurbine0_3 + 2 puissancePompe0_3
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
 c21:                           hauteurChute0_0  = 400
 c22:                           - 0.00132244897959184 puissanceTurbine0_0
                                - 0.00163265306122449 puissancePompe0_0  = 0
 c23:                           - 0.00132244897959184 puissanceTurbine0_1
                                - 0.00163265306122449 puissancePompe0_1
                                + hauteurChute0_0 - hauteurChute0_1  = 0
 c24:                           - 0.00132244897959184 puissanceTurbine0_2
                                - 0.00163265306122449 puissancePompe0_2
                                + hauteurChute0_1 - hauteurChute0_2  = 0
 c25:                           - 0.00132244897959184 puissanceTurbine0_3
                                - 0.00163265306122449 puissancePompe0_3
                                + hauteurChute0_2 - hauteurChute0_3  = 0
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
 400 <= hauteurChute0_0 <= 600
 400 <= hauteurChute0_1 <= 600
 400 <= hauteurChute0_2 <= 600
 400 <= hauteurChute0_3 <= 600
Binaries
 modePompe0_0  modeTurbine0_0  modePompe0_1  modeTurbine0_1  modePompe0_2 
 modeTurbine0_2  modePompe0_3  modeTurbine0_3 
End
