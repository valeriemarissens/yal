package yal.arbre.expressions;

public abstract class Comparaison extends Expression {
    Expression expressionGauche;
    Expression expressionDroite;

    protected Comparaison(int n) {
        super(n);
    }

    public Comparaison(Expression e1, Expression e2, int i){
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;
    }

    public abstract String getOperateur();

    @Override
    public void verifier() {

    }

    @Override
    public String toMIPS() {
        return null;
    }
}
