package yal.tableSymboles;

public class EntreeVariableLocale extends Entree{

    public EntreeVariableLocale(String idf, int n){
        super(idf, n);
        nbParametres = -1;
    }

    @Override
    public boolean estFonction() {
        return false;
    }
}
