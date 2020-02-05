package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Comparaison;

public class Condition extends Instruction {
    BlocDInstructions blocSi;
    BlocDInstructions blocSinon;
    Comparaison expression;
    private boolean estInutile = false;

    public Condition(Comparaison exp, BlocDInstructions bsi, BlocDInstructions bsinon, int n){
        super(n);
        expression = exp;
        blocSi = bsi;
        blocSinon = bsinon;

    }

    public Condition(int n) {
        super(n);
        estInutile = true;
    }

    @Override
    public void verifier() {
        blocSi.verifier();
        blocSinon.verifier();
    }

    @Override
    public String toMIPS() {
        if (estInutile){
            return null;
        }
        return null;
    }
}
