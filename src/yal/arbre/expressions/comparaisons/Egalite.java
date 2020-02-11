package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.CalculBooleen;
import yal.exceptions.MessagesErreursSemantiques;

public class Egalite extends CalculBooleen {
    private String operateur;
    private boolean difference = false;

    public Egalite(Expression e1, Expression e2, String op, int i){
        super(e1, e2, i);
        operateur = op;
        if (op.equals("!=")) difference = true;
    }



    public String titreOperation(){
        return " égalité entre deux expressions. \n";
    }

    @Override
    public String calculOperation() {
        StringBuilder code = new StringBuilder();

        // On commence par soustraire les deux nombres :
        // $v0 = 0 si les nombres sont égaux
        code.append("\t subu $v0, $v0, $t8 \n"); // (subu au lieu de sub évite les overflow

        // $v0 devient 1 si les nombres ne sont pas égaux.
        code.append("\t sltu $v0, $0, $v0 \n");

        if (!difference) {
            // Devient 0 si c'est 1 et inversement
            code.append("\t xori $v0, $v0, 1 \n");
        }

        return code.toString();
    }


    @Override
    public String toString(){
        return expGauche.toString()+" "+operateur+" "+expDroite;
    }

    @Override
    public String getOperateur() {
        return operateur;
    }
}
