package yal.arbre.declarations;

import yal.tableSymboles.*;

public class DeclarationConstante extends Declaration {

    public DeclarationConstante(String idf, int n) {
        super(n);
        entree = new EntreeVariable(idf, n + 1) ;
        symbole = new SymboleVariable() ;
    }

    @Override
    public void verifier() {}

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType() {
        return "DeclarationConstante";
    }

    @Override
    public boolean contientRetourne() {
        return false;
    }
}
