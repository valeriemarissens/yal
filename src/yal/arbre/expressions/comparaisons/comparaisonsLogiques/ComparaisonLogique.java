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

    /*@Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        mips.append("\t # ");
        mips.append(toString());
        mips.append("\n");

        mips.append(expressionGauche.toMIPS());
        mips.append("\t sw $v0, 0($sp) \t# empiler $v0 \n");
        mips.append("\t add $sp, $sp, -4 \n");

        mips.append(expressionDroite.toMIPS());
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t lw $t8, ($sp) \t\t # dépiler dans $t8 \n");

        mips.append("\t");
        mips.append(calculOperation());
        mips.append("\n");


        mips.append("\t # fin ");
        mips.append(toString());
        mips.append("\n");

        return mips.toString();
    }*/

    @Override
    public String getType() {
        return "ComparaisonLogique";
    }
}
