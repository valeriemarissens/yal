package yal.arbre.expressions;

import yal.tableSymboles.Entree;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

public abstract class AppelTableau extends Variable {

    public AppelTableau(String idf, int noLigne) {
        super(idf, noLigne);
    }

    @Override
    public void verifier() {
        chercherSymbole();
        super.verifier();
    }

    public String toMIPSDebutTableau(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On va chercher l'adresse de la première valeur du tableau. \n");
        mips.append("\t la $t3, " + (symbole.getDeplacement()+8) + registre + "\n" );
        return mips.toString();
    }
}
