package yal.arbre;

import java.util.ArrayList;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait {
    protected ArrayList<ArbreAbstrait> instructions;

    public BlocDInstructions(int n) {
        super(n) ;
        instructions = new ArrayList<>() ;
    }
    
    public void ajouter(ArbreAbstrait a) {
        instructions.add(a) ;
    }
    
    @Override
    public String toString() {
        return instructions.toString() ;
    }

    @Override
    public void verifier() {
        for (ArbreAbstrait i : instructions){
            i.verifier();
        }
    }
    
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        for (ArbreAbstrait instruction : instructions){
            mips.append("\t "+instruction.toMIPS()+"\n\n");
        }

        return mips.toString();
    }

}
