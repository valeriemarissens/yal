package yal.tableSymboles;

public class EntreeFonction extends Entree {
    private String idf;

    public EntreeFonction(String i, int n) {
        super(i, n);
        idf = i;
        nbParametres = 0;
    }

    @Override
    public boolean estFonction() {
        return true;
    }
}
