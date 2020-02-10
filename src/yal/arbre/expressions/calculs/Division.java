package yal.arbre.expressions.calculs;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public class Division extends Calcul {

    public Division(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    /**
     * Vérifie que l'expression de droite ne soit
     * pas égale à 0.
     */
    @Override
    public void verifier(){
        Expression expDroite = getExpDroite();
        String typeExpDroite = expDroite.getType();
        if (typeExpDroite.equals("ConstanteEntiere")){
            ConstanteEntiere constanteEntiere = (ConstanteEntiere) expDroite;
            if (constanteEntiere.toString().equals("0")){
                MessagesErreursSemantiques.getInstance().ajouter(noLigne,"La division par 0 est interdite.");
            }
        }
    }

    @Override
    public String titreOperation() {
        return "division "+toString()+"\n";
    }

    @Override
    public String calculOperation() {
        StringBuilder calcul = new StringBuilder();
        calcul.append("\t div $t8, $v0 \t\t # $t8 / $v0 \n");
        calcul.append("\t mflo $v0  \t\t# on range le quotien dans $v0 \n");
        return calcul.toString();
    }

    @Override
    public String getOperateur() {
        return "/";
    }
}
