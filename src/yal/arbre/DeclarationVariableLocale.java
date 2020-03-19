package yal.arbre;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;
import yal.tableSymboles.TDS;

public class DeclarationVariableLocale extends ArbreAbstrait {
    private EntreeVariable entree;
    private SymboleVariable symbole;

    /**
     * La déclaration dans la TDS se fait dans EnsembleVariablesLocales car besoin d'avoir le n° de bloc de la fonction
     * à laquelle cette variable appartient.
     *
     * @param idf
     * @param n
     */
    public DeclarationVariableLocale(String idf, int n) {
        super(n);
        entree = new EntreeVariable(idf, n);
        symbole = new SymboleVariable();
    }

    public EntreeVariable getEntree() {
        return entree;
    }

    public SymboleVariable getSymbole(){
        return symbole;
    }

    @Override
    public void verifier() {}

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType(){
        return "DeclarationVariableLocale";
    }

    @Override
    public boolean contientRetourne() {
        return false;
    }
}
