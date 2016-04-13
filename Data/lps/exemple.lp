\ENCODING=ISO-8859-1
\Problem name: Exemple

Minimize
 obj: 4 xO1a + xO1b + 2 xO2a + 3 xO2b + 130000 yO1a + 90000 yO1b + 80000 yO2a
      + 140000 yO2b + 150000 yOba
Subject To
 ctr_1: xO1a + xO2a + xOba <= 150000
 ctr_2: xO1b + xO2b - xOba <= 50000
 ctr_3: xO1a + xO1b  = 75000
 ctr_4: xO2a + xO2b  = 75000
 ctr_5: xO1a - 70000 yO1a <= 0
 ctr_6: xO2a - 30000 yO2a <= 0
 ctr_7: xO1b - 40000 yO1b <= 0
 ctr_8: xO2b - 80000 yO2b <= 0
 ctr_9: xOba - 100000 yOba <= 0
Bounds
 0 <= yO1a <= 1
 0 <= yO1b <= 1
 0 <= yO2a <= 1
 0 <= yO2b <= 1
 0 <= yOba <= 1
Binaries
 yO1a  yO1b  yO2a  yO2b  yOba 
End
