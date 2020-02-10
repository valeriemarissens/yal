package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;

public class Egalite extends Expression {

    Expression expressionGauche;
    Expression expressionDroite;
    String operateur;
    private boolean difference = false;

    protected Egalite(int n) {
        super(n);
    }

    public Egalite(Expression e1, Expression e2, String op, int i){
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;
        operateur = op;
        if (op.equals("!=")) difference = true;
    }

    @Override
    public void verifier() {
        expressionGauche.verifier();
        expressionDroite.verifier();
    }

    @Override
    public String toMIPS() {
        StringBuilder code = new StringBuilder();

        code.append("\t # Début de comparaison entre deux expressions. \n");
        // Le résultat de expressionDroite est gardé dans $v0.
        code.append(expressionDroite.toMIPS());

        // Mais on aura besoin de $v0 pour expressionGauche, alors on le met dans $t4.
        code.append("\t move $t4, $v0 \n");

        // Maintenant, on garde le résultat de expressionGauche dans $v0.
        code.append(expressionGauche.toMIPS());

        // Et on peut faire la comparaison.
        // Pour cela, on commence par soustraire les deux nombres :
        // $v0 = 0 si les nombres sont égaux
        code.append("\t subu $v0, $v0, $t4 \n"); // (subu au lieu de sub évite les overflow

        // $v0 devient 1 si les nombres ne sont pas égaux.
        code.append("\t sltu $v0, $0, $v0 \n");

        if (!difference) {
            // (cas où on teste ==)
            // $v0 doit être à 0 dans Condition si c'est faux. Donc on va inverser :
            code.append("\t xori $v0, $v0, 1 \n");
        }

        return code.toString();
    }

    @Override
    public String getType() {
        return "Egalite";
    }

    @Override
    public String toString(){
        return expressionGauche.toString()+" "+operateur+" "+expressionDroite;
    }
}
