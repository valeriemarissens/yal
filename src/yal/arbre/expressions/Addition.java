package yal.arbre.expressions;

public class Addition extends Expression {
    private Expression e1;
    private Expression e2;

    public Addition(Expression e1, Expression e2, int i) {
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

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        mips.append("\t # Addition \n");
        mips.append(e1.toMIPS());
        mips.append(e2.toMIPS());
        mips.append("\t add $v0, $v0, $t8  \t# on range l'addition dans $v0 \n");
        return mips.toString();
    }

    @Override
    public String toString(){
        return e1.toString()+" + "+e2.toString();
    }
}
