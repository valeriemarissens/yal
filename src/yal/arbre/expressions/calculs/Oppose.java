package yal.arbre.expressions.calculs;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public class Oppose extends Expression {
    private Expression e;
    private Soustraction soustraction;

    public Oppose(Expression e, int n) {
        super(n);
        this.e = e;
        ConstanteEntiere zero = new ConstanteEntiere("0", n);
        soustraction = new Soustraction(zero, e, n);
    }

    @Override
    public void verifier() {
        if (e.getType().equals("Comparaison")){
            String messageExplicite = "L'opérateur unaire - doit être appliqué à un entier.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,messageExplicite);
        }
    }

    @Override
    public String toMIPS() {
        return soustraction.toMIPS();
    }

    @Override
    public String getType() {
        return "Oppose";
    }
}
