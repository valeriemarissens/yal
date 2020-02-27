package yal.tableSymboles;

import java.util.Objects;

public class EntreeFonction extends Entree {
    private String idf;

    public EntreeFonction(String i, int n) {
        super(i, n);
        idf = i;
        nbParametres = 0;
    }


}
