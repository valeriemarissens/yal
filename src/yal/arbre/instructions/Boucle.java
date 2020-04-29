package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;
import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.CalculBooleen;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

public class Boucle extends Instruction {
    private Expression expression;
    private ArbreAbstrait bloc;
    private int identifiant;
    private String nomEtiquetteRepeter;
    private String nomEtiquetteFinTantQue;

    protected Boucle(int n) {
        super(n);
    }

    @Override
    public String getType() {
        return "Boucle";
    }

    @Override
    public boolean contientRetourne() {
        return bloc.contientRetourne();
    }

    public Boucle(Expression exp, ArbreAbstrait blocIns, int n){
        super(n);
        expression = exp;
        bloc = blocIns;
        identifiant = FabriqueIdentifiants.getInstance().getNumeroBoucle();
        nomEtiquetteRepeter="repeter"+identifiant;
        nomEtiquetteFinTantQue="fintantque"+identifiant;
    }

    @Override
    public void verifier() {
        expression.verifier();
        if (!expression.estBooleen()) {
            int ligneErreur = expression.getNoLigne();
            MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,"condition mal exprimée (penser aux parenthèses si besoin).");
        }
        bloc.verifier();
    }

    /**
     * Évaluation de la condition, le résultat est dans $v0.
     *
     * bne $v0, $0, etiquette == si $v0 # 0 (donc $v0 = 1, vrai), alors branchement à l'étiquette.
     *
     * @return
     */
    private String toStringCondition(){
        StringBuilder code = new StringBuilder();

        // Évaluation de la condition, le résultat est dans $v0
        code.append((expression).toMIPS());
        code.append("\t bne $v0, $0, ");
        code.append(nomEtiquetteRepeter);
        code.append("\n");

        return code.toString();
    }

    private String toStringJumpFinTantQue(){
        StringBuilder code = new StringBuilder();

        code.append("\t j ");
        code.append(nomEtiquetteFinTantQue);
        code.append("\n\n");

        return code.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public String toMIPS() {
        StringBuilder code = new StringBuilder();

        code.append("# Évaluation condition de boucle et branchement \n");
        code.append(toStringCondition());

        // Saut à fintantque
        code.append(toStringJumpFinTantQue());

        code.append(nomEtiquetteRepeter);
        code.append(" : \n");
        code.append(bloc.toMIPS());

        code.append("# Re-calcul de la condition de la boucle \n");
        code.append(toStringCondition());

        // Saut à fintantque
        code.append(toStringJumpFinTantQue());

        code.append(nomEtiquetteFinTantQue);
        code.append(" : ");
        code.append("\n\n");

        return code.toString();
    }

    public ArbreAbstrait getBloc(){
        return bloc;
    }

}
