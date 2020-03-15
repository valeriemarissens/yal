package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.TDS;

public class Retourne extends Instruction {
    private Expression expression;
    private int nbVariablesLocales;

    public Retourne(Expression e, int n) {
        super(n);
        expression=e;
        nbVariablesLocales = 0;
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
     * Faire le retour est plus simple (et intuitif ?) que de parcourir les instructions d'une fonction
     * et d'arrêter de les écrire dès qu'il y a le retourne (et surtout arrange les cas où il y a un retourne
     * dans une condition par ex, je crois, à tester).
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        mips.append("# On stocke le résultat de notre fonction dans $v0. \n");
        mips.append(expression.toMIPS());
        mips.append("\t # Le résultat de notre fonction est maintenant dans $v0. \n");
        mips.append("\n");

        mips.append("\t # On retourne d'où on vient. \n");

        // Il faut dépiler les variables locales de s2 puis récupérer l'adresse de retour de sp.

        int deplacementADepiler = 4 * (nbVariablesLocales);
        mips.append("\t add $s2, $s2, "+deplacementADepiler+" \n");
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t lw $ra, 0($sp) \t\t # dépiler dans $ra \n");
        mips.append("\t jr $ra \n");

        TDS.getInstance().sortieBloc();

        return mips.toString();
    }

    @Override
    public String getType() {
        return "Retourne";
    }

    @Override
    public boolean contientRetourne() {
        return true;
    }

    public void setNbVariablesLocales(int nbVariablesLocales) {
        this.nbVariablesLocales = nbVariablesLocales;
    }
}
