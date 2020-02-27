package yal.arbre;

import java.util.ArrayList;
import java.util.Iterator;

public class BlocDeclFonctions extends ArbreAbstrait implements Iterable<DeclarationFonction> {

    ArrayList<DeclarationFonction> fonctions;

    public BlocDeclFonctions(int n) {
        super(n);
        fonctions = new ArrayList<>();
    }


    public void ajouter(DeclarationFonction f) {
        fonctions.add(f) ;
    }

    @Override
    public void verifier() {
        for (DeclarationFonction f : fonctions){
            f.verifier();
        }
    }

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType() {
        return "BlocDeclFonctions";
    }

    @Override
    public Iterator<DeclarationFonction> iterator() {
        return fonctions.iterator();
    }
}
