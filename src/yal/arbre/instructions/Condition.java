package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;
import yal.arbre.BlocDInstructions;
import yal.arbre.expressions.Expression;

public class Condition extends Instruction {
    ArbreAbstrait blocSi;
    ArbreAbstrait blocSinon;
    Expression expression;
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
    public Condition(Expression exp, ArbreAbstrait bsi, ArbreAbstrait bsinon, int n){
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
        if (!expression.getType().equals("Comparaison")){
            // Erreur sémantique
        }
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
