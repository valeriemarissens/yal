package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.Variable;

public class Affectation extends Instruction {
    private Variable partieGauche;
    private Expression partieDroite;
    private String registre;

    public Affectation(String id, Expression e, int n) {
        super(n);
        partieGauche = new Variable(id,n);
        partieDroite = e;
        registre = "($s7)";
    }

    /**
     * Vérifie chacune des parties.
     * Dans le futur : vérifier que les types correspondent.
     */
    @Override
    public void verifier() { //TDS.identifier

        partieGauche.verifier();
        partieDroite.verifier();

        if (partieGauche.estVariableLocale()){
            registre = "($s2)";
        }

        if (partieGauche.estParametre()){
            registre = "($sp)";
        }
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
        mips.append("\t sw $v0, "+deplacementGauche+registre+" \n");

        return mips.toString();
    }

    @Override
    public String getType() {
        return "Affectation";
    }

}
