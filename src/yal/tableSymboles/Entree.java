package yal.tableSymboles;

import java.util.Objects;

public abstract class Entree {
    private int ligne;
    protected String idf;
    protected int nbParametres;

    public Entree(String nom, int n){
        ligne = n;
        idf = nom;
    }

    public void setIdf(String variable) {
        this.idf = variable;
    }

    public void setNbParametres(int nbParametres) {
        this.nbParametres = nbParametres;
    }

    public int getLigne() {
        return ligne;
    }

    public String getIdf() { return idf;}

    public int getNbParametres() {return nbParametres; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entree entree = (Entree) o;
        return nbParametres == entree.nbParametres &&
                Objects.equals(idf, entree.idf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idf);
    }

    public abstract boolean estFonction();
}
