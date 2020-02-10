package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.CalculBooleen;

public class Egalite extends CalculBooleen {

    String operateur;
    private boolean difference = false;

    public Egalite(Expression e1, Expression e2, String op, int i){
        super(e1, e2, i);
        operateur = op;
        if (op.equals("!=")) difference = true;
    }

    @Override
    public void verifier() {
        expGauche.verifier();
        expDroite.verifier();
    }

    public String titreOperation(){
        return " égalité entre deux expressions. \n";
    }

    @Override
    public String calculOperation() {
        StringBuilder code = new StringBuilder();

        // On commence par soustraire les deux nombres :
        // $v0 = 0 si les nombres sont égaux
        code.append("\t subu $v0, $v0, $t4 \n"); // (subu au lieu de sub évite les overflow

        // $v0 devient 1 si les nombres ne sont pas égaux.
        code.append("\t sltu $v0, $0, $v0 \n");

        if (!difference) {
            // (cas où on teste ==)
            // $v0 doit être à 0 dans Condition si c'est faux. Donc on va inverser :
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
