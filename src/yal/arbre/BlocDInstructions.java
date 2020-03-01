package yal.arbre;

import yal.exceptions.MessagesErreursSemantiques;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 21 novembre 2018
 *
 * @author brigitte wrobel-dautcourt
 */

public class BlocDInstructions extends ArbreAbstrait implements Iterable<ArbreAbstrait> {
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

    @Override
    public String getType() {
        return "BlocDInstructions";
    }


    @Override
    public Iterator<ArbreAbstrait> iterator() {
        return instructions.iterator();
    }

    public boolean contientRetourne(){
        for (ArbreAbstrait i : instructions){
            if (i.contientRetourne()){
                return true;
            }
        }
        return false;
    }

}
