package yal.arbre.expressions;

import yal.arbre.DeclarationVariable;
import yal.tableSymboles.EntreeVariable;

//Pour l'instant ne sert à rien...
public class Parametre extends DeclarationVariable {

    public Parametre(String id, int n) {
        super(id, n);

        Variable p = new Variable(id, n) ;

    }

    @Override
    public String getType() {
        return "Parametre";
    }
}
