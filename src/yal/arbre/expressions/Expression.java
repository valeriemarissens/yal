package yal.arbre.expressions;

import yal.arbre.ArbreAbstrait;

public abstract class Expression extends ArbreAbstrait {

    protected boolean estBooleen;
    protected boolean estTableau;

    protected Expression(int n) {
        super(n) ;
    }

    public abstract void verifier();

    public abstract String getType();

    public boolean estTableau(){
        return estTableau;
    }

    public Boolean estBooleen(){
        return estBooleen;
    }

    public boolean contientRetourne(){
        return false;
    }
}
