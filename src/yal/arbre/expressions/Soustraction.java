package yal.arbre.expressions;

public class Soustraction extends Expression {
    private Expression e1;
    private Expression e2;

    public Soustraction(Expression e1, Expression e2, int i) {
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

        mips.append("\t # Soustraction "+toString()+"\n");
        mips.append(e1.toMIPS());
        mips.append("\t sw $v0, 0($sp) \t# empiler $v0 \n");
        mips.append("\t add $sp, $sp, -4 \n");
        mips.append(e2.toMIPS());
        mips.append("\t add $sp, $sp, 4 \n");
        mips.append("\t lw $t8, ($sp) \t\t # dépiler dans $t8 \n");
        mips.append("\t sub $v0, $t8, $v0 \t\t # $v0 <- $t8 - $v0 \n");

        return mips.toString();
    }

    @Override
    public String toString(){
        return e1.toString()+" - "+e2.toString();
    }
}
