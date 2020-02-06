package yal.arbre.expressions.calculs;

import yal.arbre.expressions.Expression;

/**
 * Design Pattern Template Method.
 */
public abstract class Calcul extends Expression {
    private Expression expGauche;
    private Expression expDroite;

    protected Calcul(Expression e1, Expression e2, int i) {
        super(i);
        this.expGauche = e1;
        this.expDroite = e2;
    }

    /**
     * Opérandes et résultat entiers.
     */
    @Override
    public void verifier() {}

    /**
     * A faire : optimisation si val opérande droite = cste
     * dans le cas des mult et div ?
     * Pb : donne tout le temps 0...
     * @return
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        mips.append("\t # "+titreOperation());
        mips.append(expGauche.toMIPS());
        mips.append("\t sw $v0, 0($sp) \t# empiler $v0 \n");
        mips.append("\t add $sp, $sp, -4 \n");
        mips.append(expDroite.toMIPS());
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t lw $t8, ($sp) \t\t # dépiler dans $t8 \n");
        mips.append(calculOperation());
        mips.append("\t # fin "+titreOperation());

        return mips.toString();
    }

    abstract String titreOperation();

    abstract String calculOperation();

    @Override
    public String toString(){
        return expGauche.toString()+ getOperateur()+ expDroite.toString();
    }

    public abstract String getOperateur();

    public String getType(){
        return "Calcul";
    }
}
