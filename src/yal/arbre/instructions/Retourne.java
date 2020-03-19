package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.TDS;

public class Retourne extends Instruction {
    private Expression expression;
    private int nbVariablesLocales;
    private int nbParametres;

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

        mips.append("\t # On prépare le départ de la fonction. \n \n");

        // Il faut dépiler les variables locales de s2 puis récupérer l'adresse de retour de sp.

        if (nbVariablesLocales > 0) {
            int deplacementADepiler = 4 * nbVariablesLocales;
            mips.append("\t # On dépile les variables locales. \n ");
            mips.append("\t add $s2, $s2, " + deplacementADepiler + " \n");
            mips.append("\t add $sp, $sp, " + deplacementADepiler + " \n");
            mips.append("\n");
        }

        mips.append("\t # On dépile l'adresse de retour. \n");
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t lw $ra, 0($sp) \t\t # dépiler dans $ra \n \n");

        if (nbParametres > 0) {
            int deplacementADepilerParametres = 4 * nbParametres;
            mips.append("\t # On dépile les paramètres. \n ");
            mips.append("\t add $s3, $s3, " + deplacementADepilerParametres + " \n");
            mips.append("\t add $sp, $sp, " + deplacementADepilerParametres + " \n");
            mips.append("\n \n");
        }


        mips.append("\t # On se rend à l'adresse de retour. \n");
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

    public void setNbParametres(int nbParametres) { this.nbParametres = nbParametres; }

    public void setNbVariablesLocales(int nbVariablesLocales) {
        this.nbVariablesLocales = nbVariablesLocales;
    }
}
