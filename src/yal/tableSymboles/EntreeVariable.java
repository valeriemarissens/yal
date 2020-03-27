package yal.tableSymboles;


public class EntreeVariable extends Entree {

    public EntreeVariable(String v, int n){
        super(v, n);
        nbParametres = -1; // sert à distinguer idf d'une variable et idf d'une fonction (car peuvent avoir le même nom)
    }

    @Override
    public boolean estFonction() {
        return false;
    }

    @Override
    public String getType() {
        return "EntreeVariable";
    }


}
