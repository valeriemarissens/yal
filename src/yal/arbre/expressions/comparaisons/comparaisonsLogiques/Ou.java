package yal.arbre.expressions.comparaisons.comparaisonsLogiques;

import yal.arbre.expressions.Expression;

public class Ou extends ComparaisonLogique {

    public Ou(Expression e1, Expression e2, int i){
        super(e1, e2, i);
    }

    @Override
    String calculOperation() {
        return " or $v0, $v0, $t4";
    }

    @Override
    String getOperateur() {
        return " ou ";
    }
}