package yal.tableSymboles;

public class EntreeFonction extends Entree {
    private String idf;


    public EntreeFonction(String i, int n, int nbParametres) {
        super(i, n);
        idf = i;
        this.nbParametres = nbParametres;
    }

    @Override
    public boolean estFonction() {
        return true;
    }

    @Override
    public String getType() {
        return "EntreeFonction";
    }
}
