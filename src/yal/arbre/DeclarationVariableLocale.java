package yal.arbre;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;

public class DeclarationVariableLocale extends DeclarationVariable {
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
        super(idf, n);
        entree = new EntreeVariable(idf, n);
        symbole = new SymboleVariable();
    }

    public EntreeVariable getEntree() {
        return entree;
    }

    public SymboleVariable getSymbole(){
        return symbole;
    }
}
