package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;

/**
 * Design Pattern Template Method.
 */
public abstract class Calcul extends Expression {
    protected Expression expGauche;
    protected Expression expDroite;
    private boolean estDroiteCste = false;

    protected Calcul(Expression e1, Expression e2, int i) {
        super(i);
        this.expGauche = e1;
        this.expDroite = e2;
        estDroiteCste = expDroite.getType().equals("ConstanteEntiere");
        estBooleen = false;
    }

    /**
     * Règle 13 sémantique : opérandes et résultat entiers.
     */
    @Override
    public void verifier() {
        expGauche.verifier();
        expDroite.verifier();

        boolean pasEntierGauche = expGauche.estBooleen();
        boolean pasEntierDroite = expDroite.estBooleen();
        if (pasEntierDroite || pasEntierGauche) {
            String messageExplicite = "Les opérandes de +, -, *, / sont des entiers, pas des booléens.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        mips.append("\n");
        mips.append("\t # Début : ");
        mips.append(titreOperation());
        mips.append(expGauche.toMIPS());

        if (estDroiteCste){
            mips.append("\t move $t8, $v0 \n");
            mips.append("\t li $v0, ");
            mips.append(expDroite.toString()); // donne juste sa valeur, pas son code
            mips.append("\n");

        }else {
            mips.append("\t sw $v0, 0($sp) \t# empiler $v0 \n");
            mips.append("\t add $sp, $sp, -4 \n");
            mips.append(expDroite.toMIPS());
            mips.append("\t add $sp, $sp, 4 \n");
            mips.append("\t lw $t8, ($sp) \t\t # dépiler dans $t8 \n");
        }
            mips.append(calculOperation());
            mips.append("\t # Fin : ");
            mips.append(titreOperation());


        return mips.toString();
    }

    public abstract String titreOperation();

    public abstract String calculOperation();

    @Override
    public String toString(){
        return expGauche.toString()+ getOperateur()+ expDroite.toString();
    }

    public abstract String getOperateur();

    public String getType(){
        return "Calcul";
    }

    public Expression getExpDroite(){
        return expDroite;
    }
}
