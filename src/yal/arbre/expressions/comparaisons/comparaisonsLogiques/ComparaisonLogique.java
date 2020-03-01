package yal.arbre.expressions.comparaisons.comparaisonsLogiques;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.Calcul;
import yal.exceptions.MessagesErreursSemantiques;

/**
 * ET, OU, NON héritent de ComparaisonLogique.
 */
public abstract class ComparaisonLogique extends Calcul {
    private Expression expressionGauche;
    private Expression expressionDroite;

    public ComparaisonLogique(Expression e1, Expression e2, int i) {
        super(e1,e2,i);
        expressionGauche = e1;
        expressionDroite = e2;
        estBooleen = true;
    }

    /**
     * Règle 13 sémantique : opérandes et résultat booléens.
     */
    @Override
    public void verifier() {
        expressionGauche.verifier();
        expressionDroite.verifier();

        if ( !expressionGauche.estBooleen() || !expressionDroite.estBooleen() ){
            String messageExplicite = "Les opérandes de 'et', 'oui' et 'non' doivent être des booléens et pas des entiers.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,messageExplicite);
        }
    }

    @Override
    public String getType() {
        return "ComparaisonLogique";
    }
}
