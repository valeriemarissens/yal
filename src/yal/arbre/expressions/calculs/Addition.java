package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;

public class Addition extends Calcul {

    public Addition(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    String titreOperation() {
        return "addition "+toString()+"\n";
    }

    @Override
    String calculOperation() {
        return "\t add $v0, $t8, $v0 \t\t" +
                " # $v0 <- $t8 + $v0 \n";
    }

    public String getOperateur() {
        return "+";
    }

}