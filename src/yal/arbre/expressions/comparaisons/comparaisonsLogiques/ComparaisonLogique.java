package yal.arbre.expressions.comparaisons.comparaisonsLogiques;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

public abstract class ComparaisonLogique extends Expression {
    private Expression expressionGauche;
    private Expression expressionDroite;

    public ComparaisonLogique(Expression e1, Expression e2, int i) {
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;
    }

    @Override
    public void verifier() {
        expressionGauche.verifier();
        expressionDroite.verifier();

        boolean pasBooleenGauche = expressionGauche.getType().equals("Calcul");
        boolean pasBooleenDroite = expressionDroite.getType().equals("Calcul");
        if (pasBooleenGauche || pasBooleenDroite){
            String messageExplicite = "Les opérandes de 'et', 'oui' et 'non' doivent être des booléens et pas des entiers.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,messageExplicite);
        }
    }

    @Override
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
    }

    abstract String calculOperation();

    abstract String getOperateur();

    @Override
    public String getType() {
        return "ComparaisonLogique";
    }

    @Override
    public String toString(){
        return expressionGauche.toString()+getOperateur()+expressionDroite.toString();
    }
}
