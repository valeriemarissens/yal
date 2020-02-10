package yal.arbre.expressions.comparaisons.comparaisonsLogiques;

import yal.arbre.expressions.Expression;

public class Et extends ComparaisonLogique {

    public Et(Expression e1, Expression e2, int i){
        super(e1, e2, i);
    }

    /**
     * Dans ce cas, le toString() renvoie la même chose.
     * @return e1 et e2.
     */
    @Override
    public String titreOperation() {
        return toString();
    }

    @Override
    public String calculOperation() {
        return "\t and $v0, $v0, $t8 \t\t # $v0 <- $v0 et $t8 \n";
    }

    @Override
    public String getOperateur() {
        return " et ";
    }
}
