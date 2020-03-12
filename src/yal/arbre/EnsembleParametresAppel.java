package yal.arbre;

import yal.arbre.expressions.Expression;

import java.util.ArrayList;

public class EnsembleParametresAppel {
    private int noLigne;
    ArrayList<Expression> parametres;

    public EnsembleParametresAppel(int noLigne){
        this.noLigne = noLigne;
        parametres = new ArrayList<>();
    }

    public void verifier(){
        for (Expression expression : parametres){
            expression.verifier();
        }
    }

    public void ajouter(Expression expression){
        parametres.add(expression);
    }

    public int getNbParametresAppel(){
        return parametres.size();
    }
}
