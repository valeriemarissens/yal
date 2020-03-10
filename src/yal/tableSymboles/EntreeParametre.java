package yal.tableSymboles;

public class EntreeParametre extends Entree {

    public EntreeParametre(String nom, int n) {
        super(nom, n);
    }

    @Override
    public boolean estFonction() {
        return false;
    }
}
