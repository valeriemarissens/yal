package yal.arbre;

import yal.arbre.expressions.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class EnsembleParametresAppel implements Iterable<Expression> {
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

    public void reverse(){
        Collections.reverse(parametres);
    }

    public void ajouter(Expression param){
        parametres.add(param);
    }

    public int getNbParametresAppel(){
        return parametres.size();
    }

    @Override
    public Iterator<Expression> iterator() {
        return parametres.iterator();
    }
}
