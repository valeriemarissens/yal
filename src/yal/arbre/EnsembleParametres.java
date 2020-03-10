package yal.arbre;


import java.util.ArrayList;

public class EnsembleParametres {
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
}
