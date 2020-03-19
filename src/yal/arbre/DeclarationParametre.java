package yal.arbre;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;

public class DeclarationParametre {
    private String idf;
    private int noLigne;
    private EntreeVariable entree;
    private SymboleVariable symbole ;

    public DeclarationParametre(String idf, int noLigne){
        this.idf = idf;
        this.noLigne = noLigne;
        this.entree = new EntreeVariable(idf, noLigne);
        this.symbole = new SymboleVariable();
        symbole.setEstParametre(true);
    }

    public EntreeVariable getEntree(){
        return entree;
    }

    public SymboleVariable getSymbole(){
        return symbole;
    }

    public String getIdf(){
        return idf;
    }

    public int getNoLigne(){
        return noLigne;
    }

    public void verifier(){}
}
