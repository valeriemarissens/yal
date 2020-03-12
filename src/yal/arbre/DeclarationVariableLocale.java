package yal.arbre;

import yal.tableSymboles.EntreeVariableLocale;

public class DeclarationVariableLocale extends ArbreAbstrait {
    private EntreeVariableLocale entree;
    private String idf;
    protected DeclarationVariableLocale(int n) {
        super(n);
    }

    public DeclarationVariableLocale(String idf, int n){
        super(n);
        this.idf= idf;
    }

    @Override
    public void verifier() {

    }

    public String getIdf(){
        return idf;
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