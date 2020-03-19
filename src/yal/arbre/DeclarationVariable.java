package yal.arbre;

import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;
import yal.tableSymboles.TDS;

public class DeclarationVariable extends ArbreAbstrait {
    EntreeVariable e;

    public DeclarationVariable(String idf, int n) {
        super(n);
        e = new EntreeVariable(idf, n+ 1) ;
        SymboleVariable s = new SymboleVariable() ;

        TDS.getInstance().ajouter(e, s);
    }

    @Override
    public void verifier() {}

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType() {
        return "DeclarationVariable";
    }

    @Override
    public boolean contientRetourne() {
        return false;
    }
}
