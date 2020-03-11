package yal.arbre;

import java.util.ArrayList;
import java.util.Iterator;

public class EnsembleVariablesLocales implements Iterable<DeclarationVariableLocale> {
    ArrayList<DeclarationVariableLocale> variablesLocales;
    int noLigne;

    public EnsembleVariablesLocales(int noLigne){
        this.noLigne = noLigne;
        variablesLocales = new ArrayList<>();
    }

    public void verifier(){
        for (DeclarationVariableLocale v : variablesLocales){
            v.verifier();
        }
    }

    public int getNbVariablesLocales(){
        return variablesLocales.size();
    }

    public void ajouter(DeclarationVariableLocale variableLocale){
        variablesLocales.add(variableLocale);
    }

    @Override
    public Iterator<DeclarationVariableLocale> iterator() {
        return variablesLocales.iterator();
    }
}
