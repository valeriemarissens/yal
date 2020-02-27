package yal.arbre.instructions;

import yal.arbre.expressions.Variable;

public class Lire extends Instruction {
    private Variable variable;


    public Lire(String id, int n) {
        super(n);
        variable = new Variable(id, n);

    }

    /**
     * Vérifie que la variable où on veut mettre
     * une constante soit déjà déclarée.
     */
    @Override
    public void verifier() {
        variable.verifier();
    }

    /**
     * On met 5 dans $v0 avant le syscall : code de read.
     * @return code correspondant à la lecture.
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder("\n");
        mips.append("\t # Lire "+variable.toString()+"\n");
        mips.append(variable.toMIPS());
        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, 5 \n");
        mips.append("\t syscall \n");
        mips.append("\t sw $v0, "+variable.getDeplacement()+"($s7)"+"\n");

        return mips.toString();
    }

    @Override
    public String getType() {
        return "Lire";
    }
}
