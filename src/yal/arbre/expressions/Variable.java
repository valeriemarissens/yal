package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;
import yal.tableSymboles.TDS;

public class Variable extends Expression {
    private EntreeVariable entreeVariable;
    protected SymboleVariable symbole;
    private boolean estVariableLocale ;
    private boolean estParametre;
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

    public boolean estParametre(){
        return estParametre;
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
            estParametre = symbole.estParametre();

            if (estVariableLocale){
                registre = "($s2)";
            } else {
                registre = "($s7)";
            }

            // L'ordre est important car tout paramètre est aussi une variable locale
            if (estParametre){
                registre = "($s3)";
            }
        }
    }

    @Override
    public String getType() {
        return "Variable";
    }


    public String getRegistre(){
        return registre;
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        // Même si on passe par vérifier, il faut quand même re-appeler vérifier pour mettre la valeur dans "registre"...
        // Sinon, registre est null pour x raison(s).
        //todo : Voir si c'est important de laisser cette ligne décommentée ? Je l'ai commenté et toutes nos
        // erreurs de sortie de fonction où les variables du pp avaient été écrasées par celles des fonctions
        // ont été résolus...
        //verifier();
        
        if (symbole != null) {
            mips.append("\t lw $v0, " + symbole.getDeplacement() + registre );
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
