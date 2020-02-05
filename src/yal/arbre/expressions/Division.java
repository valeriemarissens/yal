package yal.arbre.expressions;

public class Division extends Calcul {

    public Division(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    String titreOperation() {
        return "division "+toString()+"\n";
    }

    @Override
    String calculOperation() {
        StringBuilder calcul = new StringBuilder();
        calcul.append("\t div $t8, $v0 \t\t # $t8 / $v0 \n");
        calcul.append("\t mflo $v0  \t\t# on range le quotien dans $v0 \n");
        return calcul.toString();
    }

    @Override
    String operateur() {
        return " / ";
    }
}
