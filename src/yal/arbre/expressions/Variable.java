package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;
import yal.tableSymboles.TDS;

public class Variable extends Expression {
    private EntreeVariable entreeVariable;
    protected SymboleVariable symbole;
    private boolean estVariableLocale ;
    protected String registre;

    public Variable(String idf, int noLigne) {
        super(noLigne);
        entreeVariable = new EntreeVariable(idf, noLigne);
    }

    protected void chercherSymbole() {
        symbole = (SymboleVariable) TDS.getInstance().identifier(entreeVariable);
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
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,"La variable "+toString()+" n'a pas été déclarée.");
        }else {
            estVariableLocale = symbole.getNumeroBloc() != 0;

            estTableau = symbole.estTableau();

            if (estVariableLocale){
                registre = "($s2)";
            } else {
                registre = "($s7)";
            }
        }
    }

    @Override
    public String getType() {
        if (estTableau){
            return "Tableau";
        }
        return "Variable";

    }


    public String getRegistre(){
        return registre;
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        if (symbole != null) {
            mips.append("\t lw $v0, " + symbole.getDeplacement() + registre );
            mips.append("\t\t # on charge la valeur de " + entreeVariable.getIdf() + " dans $v0 \n");
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
