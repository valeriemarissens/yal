package yal.arbre.instructions;

import yal.arbre.expressions.Expression;

public class Ecrire extends Instruction {
    protected Expression exp ;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
    }

    @Override
    public void verifier() {
        exp.verifier();
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder("\n\t # Ecrire "+exp.toString()+"\n");
        mips.append(exp.toMIPS());
        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, 1 \n");
        mips.append("\t syscall \n");

        return mips.toString();
    }

}
