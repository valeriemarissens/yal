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

    /**
     * Le code d'écriture d'un string est 4.
     * @return code d'écriture de exp suivie d'un retour à la ligne
     * (défini dans Programme.java).
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder("\n\t # Ecrire "+exp.toString()+"\n");
        mips.append(exp.toMIPS());
        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, 1 \n");
        mips.append("\t syscall \n");
        mips.append("\t li $v0, 4 \n");
        mips.append("\t la $a0, retourLigne \n");
        mips.append("\t syscall \n");

        return mips.toString();
    }

}
