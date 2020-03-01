package yal.arbre.instructions;

public abstract class InstructionBloc extends Instruction {
    protected InstructionBloc(int n) {
        super(n);
    }

    @Override
    public void verifier() {

    }

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType() {
        return "InstructionBloc";
    }

}
