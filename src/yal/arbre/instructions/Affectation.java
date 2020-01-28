package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.Variable;

public class Affectation extends Instruction {
    private Variable partieGauche;
    private Expression partieDroite;

    public Affectation(String id, Expression e, int n) {
        super(n);
        partieGauche = new Variable(id,n);
        partieDroite = e;
    }

    /**
     * Vérifie chacune des parties.
     * Dans le futur : vérifier que les types correspondent.
     */
    @Override
    public void verifier() {
        partieGauche.verifier();
        partieDroite.verifier();
    }

    /**
     * Les déplacements de variables sont dans l'arbre,
     * la TDS ne sert plus.
     *
     * @return le code équivalent à l'affectation
     * partieGauche = partieDroite en MIPS.
     */
    @Override
    public String toMIPS() {
        int deplacementGauche = partieGauche.getDeplacement();

        StringBuilder mips = new StringBuilder();
        mips.append("# Affectation "+partieGauche.toString()+" = "+partieDroite.toString()+" \n");
        mips.append(partieDroite.toMIPS());
        mips.append("\t sw $v0, "+deplacementGauche+"($s7) \n");

        return mips.toString();
    }
}
