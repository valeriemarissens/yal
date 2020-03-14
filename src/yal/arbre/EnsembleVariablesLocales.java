package yal.arbre;

import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.Entree;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

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

    public void ajouterVariablesDansTDS(int numeroBloc){
        // On commence à énumérer toutes les variables locales d'une fonction donc il faut reset par rapport à la précédente
        FabriqueIdentifiants.getInstance().resetCompteurVariableLocale();
        for (DeclarationVariableLocale variableLocale : variablesLocales){
            Entree entree = variableLocale.getEntree();
            Symbole symbole = variableLocale.getSymbole();

            TDS.getInstance().ajouterVariableLocale(numeroBloc, entree, symbole);
        }
    }

    public void ajouter(DeclarationVariableLocale variableLocale){
        variablesLocales.add(variableLocale);
    }

    @Override
    public Iterator<DeclarationVariableLocale> iterator() {
        return variablesLocales.iterator();
    }
}
