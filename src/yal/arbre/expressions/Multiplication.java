package yal.arbre.expressions;

public class Multiplication extends Expression {
    private Expression e1;
    private Expression e2;

    public Multiplication(Expression e1, Expression e2, int i) {
        super(i);
        this.e1 = e1;
        this.e2 = e2;
    }

    /**
     * Opérandes et résultat entiers.
     */
    @Override
    public void verifier() {

    }

    /**
     * A faire : optimisation si val opérande droite = cste.
     * Pb : donne tout le temps 0...
     * @return
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        mips.append("\t # Multiplication "+toString()+"\n");
        mips.append(e1.toMIPS());
        mips.append("\t sw $v0, 0($sp) \t\t# empiler $v0 \n");
        mips.append("\t add $sp, $sp, -4 \n");
        mips.append(e2.toMIPS());
        mips.append("\t sub $sp, $sp, 4 \n");
        mips.append("\t lw $t8, ($sp) \t\t # dépiler dans $t8 \n");
        mips.append("\t mult $t8, $v0 \t\t # $t8 * $v0 \n");
        mips.append("\t mflo $v0  \t\t# on range la résultat dans $v0 \n");

        return mips.toString();
    }

    @Override
    public String toString(){
        return e1.toString()+" * "+e2.toString();
    }
}
