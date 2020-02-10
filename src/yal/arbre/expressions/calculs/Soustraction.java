package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.Calcul;

public class Soustraction extends Calcul {

    public Soustraction(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    public String titreOperation() {
        return "soustraction "+toString()+"\n";
    }

    @Override
    public String calculOperation() {
        return "\t sub $v0, $t8, $v0 \t\t # $v0 = $t8 - $v0 \n";
    }

    @Override
    public String getOperateur() {
        return "-";
    }
}
