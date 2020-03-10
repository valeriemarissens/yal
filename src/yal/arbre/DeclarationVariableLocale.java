package yal.arbre;

import yal.tableSymboles.EntreeVariableLocale;

public class DeclarationVariableLocale extends ArbreAbstrait {
    private EntreeVariableLocale entree;
    protected DeclarationVariableLocale(int n) {
        super(n);
    }

    public DeclarationVariableLocale(String idf, int n){
        super(n);
    }

    @Override
    public void verifier() {

    }

    @Override
    public String toMIPS() {
        return "";
    }

    @Override
    public String getType() {
        return "DeclarationVariableLocale";
    }

    @Override
    public boolean contientRetourne() {
        return false;
    }
}
