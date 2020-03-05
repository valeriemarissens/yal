package yal.tableSymboles;


public class EntreeVariable extends Entree {

    public EntreeVariable(String v, int n){
        super(v, n);
        nbParametres = -1;
    }

    @Override
    public boolean estFonction() {
        return false;
    }


}
