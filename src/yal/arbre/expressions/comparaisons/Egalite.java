package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;

public class Egalite extends Expression {

    Expression expressionGauche;
    Expression expressionDroite;
    String operateur;
    String cmdMips;

    protected Egalite(int n) {
        super(n);
    }

    public Egalite(Expression e1, Expression e2, String op, int i){
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;
        operateur = op;

        // Oui c'est l'inverse de ce qu'on pourrait croire,
        // mais c'est  parce qu'on se branche directement sur le sinon dans condition
        if (op.equals("==")){
            cmdMips = "bne";
        }else if (op.equals("!=")){
            cmdMips = "beq";
        }

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
        code.append("\t");
        code.append(cmdMips);
        code.append(" $v0, $t4, ");
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
