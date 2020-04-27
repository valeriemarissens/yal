package yal.arbre.declarations;


import yal.arbre.declarations.DeclarationParametre;
import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.Entree;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

import java.util.ArrayList;
import java.util.Iterator;

public class BlocParametres extends  BlocDeclarations implements Iterable<DeclarationParametre> {
    private ArrayList<DeclarationParametre> parametres;

    public BlocParametres(int noLigne){
        super(noLigne);
        this.noLigne= noLigne;
        parametres = new ArrayList<>();
    }

    public void ajouter(DeclarationParametre param){
        parametres.add(param);
    }

    public void ajouterTDS(int numeroBloc){
        TDS tds = TDS.getInstance();
        for (DeclarationParametre parametres : parametres){
            Entree entree = parametres.getEntree();
            Symbole symbole = parametres.getSymbole();
            tds.ajouter(numeroBloc, entree, symbole);
        }
    }

    public void verifier(){}

    public int getNbParametres(){
        return parametres.size();
    }

    @Override
    public Iterator<DeclarationParametre> iterator() {
        return parametres.iterator();
    }
}
