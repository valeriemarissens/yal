package yal.arbre.declarations;

import yal.arbre.ArbreAbstrait;
import yal.tableSymboles.Entree;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

public abstract class Declaration extends ArbreAbstrait {
    protected String idf;
    protected Entree entree;
    protected Symbole symbole;

    protected Declaration(String idf, int n) {
        super(n);
        this.idf = idf;
    }

    @Override
    public void verifier() {}

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public abstract String getType();

    @Override
    public boolean contientRetourne() {
        return false;
    }

    public void ajouterTDS(int numeroBloc){
        TDS.getInstance().ajouter(numeroBloc, entree, symbole);
    }

    public Entree getEntree(){
        return entree;
    }

    public Symbole getSymbole(){
        return symbole;
    }

}
