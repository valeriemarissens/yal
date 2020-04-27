package yal.arbre.declarations;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;

public class DeclarationParametre extends Declaration {


    public DeclarationParametre(String idf, int noLigne){
        super(idf, noLigne);
        entree = new EntreeVariable(idf, noLigne);
        symbole = new SymboleVariable();
    }


    @Override
    public String getType() {
        return "DeclarationParametre";
    }
}
