package yal.arbre.expressions;

import yal.arbre.ArbreAbstrait;

public abstract class Expression extends ArbreAbstrait {

    protected boolean estBooleen;
    
    protected Expression(int n) {
        super(n) ;
    }

    public abstract void verifier();

    public abstract String getType();

    public Boolean estBooleen(){
        return estBooleen;
    }

}
