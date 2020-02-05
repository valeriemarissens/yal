package yal.arbre.instructions;

import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Comparaison;

public class Condition extends Instruction {
    BlocDInstructions blocSi;
    BlocDInstructions blocSinon;
    Comparaison expression;
    private boolean estInutile = false;

    /**
     * Constructeur pour si alors () fsi, si alors () sinon fsi et si alors () sinon () fsi.
     *
     * Un seul constructeur pour pouvoir différencier la liste d'instructions venant du si
     * et la liste d'instruction venant du sinon dans les 3 cas.
     *
     * @param exp
     * @param bsi
     * @param bsinon
     * @param n
     */
    public Condition(Comparaison exp, BlocDInstructions bsi, BlocDInstructions bsinon, int n){
        super(n);
        expression = exp;
        blocSi = bsi;
        blocSinon = bsinon;
    }

    /**
     * Constructeur si syntaxe : si alors fsi (donc inutile de générer du code mips)
     * (et oui c'est syntaxiquement correct)
     * @param n
     */
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
