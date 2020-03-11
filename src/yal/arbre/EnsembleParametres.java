package yal.arbre;


import java.util.ArrayList;
import java.util.Iterator;

public class EnsembleParametres implements  Iterable<DeclarationParametre> {
    private int noLigne;
    private ArrayList<DeclarationParametre> parametres;

    public EnsembleParametres(int noLigne){
        this.noLigne= noLigne;
        parametres = new ArrayList<>();
    }

    public void ajouter(DeclarationParametre param){
        parametres.add(param);
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
