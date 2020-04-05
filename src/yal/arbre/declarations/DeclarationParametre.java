package yal.arbre.declarations;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;

public class DeclarationParametre extends Declaration {
    private String idf;
    private int noLigne;

    public DeclarationParametre(String idf, int noLigne){
        super(noLigne);
        this.idf = idf;
        this.noLigne = noLigne;
        entree = new EntreeVariable(idf, noLigne);
        symbole = new SymboleVariable();
        ((SymboleVariable)symbole).setEstParametre(true);
    }

    public String getIdf(){
        return idf;
    }

    public int getNoLigne(){
        return noLigne;
    }

    public void verifier(){}

    @Override
    public String getType() {
        return "DeclarationParametre";
    }
}
