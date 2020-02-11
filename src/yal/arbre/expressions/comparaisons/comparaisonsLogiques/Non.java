package yal.arbre.expressions.comparaisons.comparaisonsLogiques;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public class Non extends Expression{
    private Expression exp;

    public Non(Expression e, int i){
        super(i);
        exp = e;
        estBooleen = true;
    }

    /**
     * Règle 13 de la semantique.
     */
    @Override
    public void verifier() {
        exp.verifier();

        if (!exp.estBooleen()){
            String messageExplicite = "L'opérande de 'non' doit être booléen (pensez aux parenthèses).";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,messageExplicite);
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        // $v0 contient le booléen de exp.
        mips.append("\t # Début : non ("+exp+")\n");
        mips.append(exp.toMIPS());

        // $v0 contient maintenant le contraire de ce qu'il avait avant.
        mips.append("\t xori $v0, $v0, 1 \n");

        mips.append("\t # Fin : non ("+exp+")\n");

        return mips.toString();
    }

    @Override
    public String getType() {
        return "Non";
    }

    @Override
    public String toString(){
        return "non "+exp.toString();
    }
}