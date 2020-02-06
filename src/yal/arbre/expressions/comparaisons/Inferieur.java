package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;

public class Inferieur extends Comparaison {

    public Inferieur(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    public String getOperateur() {
        return "<";
    }

}
