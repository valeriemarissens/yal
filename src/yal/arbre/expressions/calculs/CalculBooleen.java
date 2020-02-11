package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public abstract class CalculBooleen extends Calcul {

    protected CalculBooleen(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
        estBooleen = true;
    }

    public abstract String titreOperation();

    public void verifier() {
        boolean type = (expGauche.estBooleen() && expDroite.estBooleen()) || (!expGauche.estBooleen() && !expDroite.estBooleen());
        if (!type) {
            String messageExplicite = "Les deux opérandes doivent être de même type.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }
        expGauche.verifier();
        expDroite.verifier();
    }

    public String getType(){
        return "CalculBooleen";
    }

}
