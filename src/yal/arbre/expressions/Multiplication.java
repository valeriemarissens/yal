package yal.arbre.expressions;

public class Multiplication extends Calcul {

    public Multiplication(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    String titreOperation() {
        return "multiplication "+toString()+"\n";
    }

    @Override
    String calculOperation() {
        StringBuilder calcul = new StringBuilder();
        calcul.append("\t mult $t8, $v0 \t\t # $t8 * $v0 \n");
        calcul.append("\t mflo $v0  \t\t# on range la résultat dans $v0 \n");
        return calcul.toString();
    }

    @Override
    String operateur() {
        return " * ";
    }
}
