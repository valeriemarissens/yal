package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

public class Variable extends Expression {
    private EntreeVariable entreeVariable;
    private Symbole symbole;
    boolean estVariableLocale ;
    public Variable(String id, int n) {
        super(n);
        entreeVariable = new EntreeVariable(id, n);
        chercherSymbole();
    }

    private void chercherSymbole() {
        symbole = TDS.getInstance().identifier(entreeVariable);
    }

    public boolean estVariableLocale(){
        return estVariableLocale;
    }
    /**
     * Vérifie que la variable soit déclarée précédemment.
     */
    @Override
    public void verifier() {
        chercherSymbole();
        if (symbole == null){
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,"La variable n'a pas été déclarée.");
        }else {
            estVariableLocale = symbole.getNumeroBloc() != 0;
        }

    }


    @Override
    public String getType() {
        return "Variable";
    }


    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        if (symbole != null && !estVariableLocale) {
            mips.append("\t lw $v0, " + symbole.getDeplacement() + "($s7)");
            mips.append("\t\t # on range la valeur de " + entreeVariable.getIdf() + " dans $v0 \n");
        }else{
            mips.append("\t lw $v0, " + symbole.getDeplacement() + "($s2)");
            mips.append("\t\t # on range la valeur de " + entreeVariable.getIdf() + " dans $v0 \n");
        }
        return mips.toString();
    }

    @Override
    public String toString(){
        return entreeVariable.getIdf();
    }

    public int getDeplacement() {
        int deplacement = 0;
        if (symbole != null){
            deplacement = symbole.getDeplacement();
        }
        return deplacement;
    }


}
