package yal.arbre.expressions;

public class Soustraction extends Calcul {

    public Soustraction(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    String titreOperation() {
        return "soustraction "+toString()+"\n";
    }

    @Override
    String calculOperation() {
        return "\t sub $v0, $t8, $v0 \t\t # $v0 <- $t8 - $v0 \n";
    }

    @Override
    String operateur() {
        return " - ";
    }
}
