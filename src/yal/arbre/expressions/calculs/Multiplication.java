package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.Calcul;

public class Multiplication extends Calcul {

    public Multiplication(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    public String titreOperation() {
        return "multiplication "+toString()+"\n";
    }

    @Override
    public String calculOperation() {
        StringBuilder calcul = new StringBuilder();
        calcul.append("\t mult $t8, $v0 \t\t # $t8 * $v0 \n");
        calcul.append("\t mflo $v0  \t\t# on range la résultat dans $v0 \n");
        return calcul.toString();
    }

    @Override
    public String getOperateur() {
        return "*";
    }
}
