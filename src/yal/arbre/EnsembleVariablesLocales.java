package yal.arbre;

import java.util.ArrayList;

public class EnsembleVariablesLocales {
    ArrayList<DeclarationVariableLocale> variablesLocales;
    int noLigne;

    public EnsembleVariablesLocales(int noLigne){
        this.noLigne = noLigne;
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
}
