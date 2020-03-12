package yal.arbre;

import yal.arbre.expressions.Expression;

public class DeclarationParametreAppel extends Expression {

    public DeclarationParametreAppel(int n) {
        super(n);
    }

    @Override
    public void verifier() {

    }

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
