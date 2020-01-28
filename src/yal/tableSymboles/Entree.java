package yal.tableSymboles;

public abstract class Entree {
    private int ligne;

    public Entree(int n){
        ligne = n;
    }

    public int getLigne() {
        return ligne;
    }
}
