package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public class Retourne extends Instruction {
    Expression expression;
    public Retourne(Expression e, int n) {
        super(n);
        expression=e;
    }

    @Override
    public void verifier() {
        if (expression.estBooleen()) {
            String messageExplicite = "Le retour d'une fonction est un entier.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }
        expression.verifier();
    }

    /**
     * S'occupe de calculer la valeur de retour de la fonction et gère le retour à l'endroit
     * où la fonction a été appelée.
     * Faire le retour est plus simple (et intuitive ?) que de parcourir les instructions d'une fonction
     * et d'arrêter de les écrire dès qu'il y a le retourne (et surtout arrange les cas où il y a un retourne
     * dans une condition par ex, je crois, à tester).
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        mips.append("# On stocke le résultat de notre fonction dans $v0. \n");
        mips.append(expression.toMIPS());
        mips.append("\n");

        mips.append("# On retourne d'où on vient. \n");
        mips.append("\t lw $ra, 0($sp) \t\t # dépiler dans $t8 \n");
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t jr $ra \n");
        return mips.toString();
    }

    @Override
    public String getType() {
        return "Retourne";
    }
}
