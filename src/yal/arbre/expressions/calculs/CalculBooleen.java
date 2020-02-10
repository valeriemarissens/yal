package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public abstract class CalculBooleen extends Calcul {

    protected CalculBooleen(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    public abstract String titreOperation();

    public void verifier(){
        String typeExpGauche = expGauche.getType();
        String typeExpDroite = expDroite.getType();

        // Règle n13 de sémantique
        if (!typeExpGauche.equals(typeExpDroite)){
            String messageExplicite = "Il faut que les deux opérandes soient de même type.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);

        }

        expGauche.verifier();
        expDroite.verifier();

    }

    public String getType(){
        return "CalculBooleen";
    }

}
