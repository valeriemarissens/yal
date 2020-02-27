package yal.arbre.instructions;

import yal.arbre.expressions.Expression;

public class Retourne extends Instruction {
    Expression expression;
    public Retourne(Expression e, int n) {
        super(n);
        expression=e;
    }

    @Override
    public void verifier() {
        // vérifier que c'est dans une fonction et nulle part ailleurs
        // mais est-ce que c'est ici qu'on le fait ?
        expression.verifier();
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        mips.append("# On stocke le résultat de notre fonction dans $v0. \n");
        mips.append(expression.toMIPS());

        // C'est DeclarationFonction qui s'occupe de dépiler et de sortir.
        return mips.toString();
    }

    @Override
    public String getType() {
        return "Retourne";
    }
}
