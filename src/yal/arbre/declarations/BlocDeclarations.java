package yal.arbre.declarations;

import yal.arbre.ArbreAbstrait;
import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.Entree;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.TDS;

import java.util.ArrayList;

/**
 * Un bloc de déclarations est instancié lorsqu'on déclare la première variable/tableau
 * dans le PP ou dans une fonction.
 */
public class BlocDeclarations extends ArbreAbstrait {
    protected ArrayList<DeclarationConstante> constantes;
    protected ArrayList<DeclarationTableau> tableaux;
    protected int numeroBloc;

    /**
     * Le numéro de bloc est set par la fonction, ou est 0 par défaut (PP).
     * @param noLigne : numéro de la ligne du début de bloc de déclarations.
     */
    public BlocDeclarations(int noLigne) {
        super(noLigne);

        /* Initialisation des collections. */
        constantes = new ArrayList<>();
        tableaux = new ArrayList<>();
    }

    /**
     * Les variables n'ont rien à vérifier.
     */
    @Override
    public void verifier() {
        for (DeclarationTableau tableau : tableaux){
            tableau.verifier();
        }
    }

    /**
     * Depuis le Grammaire.cup, on ajoute un tableau à ce bloc de déclarations.
     * @param tableau
     */
    public void ajouter(DeclarationTableau tableau){
        tableaux.add(tableau);
    }

    /**
     * Depuis le Grammaire.cup, on ajoute une constante à ce bloc de déclarations.
     * @param constante : constante à ajouter dans le bloc de déclaration.
     */
    public void ajouter(DeclarationConstante constante){
        constantes.add(constante);
    }

    @Override
    public String toMIPS() {
        return null;
    }

    @Override
    public String getType(){
        return "BlocDeclarations";
    }

    public void setNumeroBloc(int numeroBloc){
        this.numeroBloc = numeroBloc;
    }


    /**
     * Les déclarations ne devraient pas contenir de "retourne" : erreur syntaxique.
     *
     * @return false
     */
    @Override
    public boolean contientRetourne() {
        return false;
    }

    /**
     * @return le nombre de constantes dans le bloc de déclarations.
     */
    public int getNbConstantes(){
        return constantes.size();
    }

    /**
     * @return le nombre de tableaux dans le bloc de déclarations.
     */
    public int getNbTableaux(){
        return tableaux.size();
    }

    public void ajouterTDS(){
        ajouterConstantes();
        ajouterTableaux();
    }

    /**
     * Ajoute les constantes dans la TDS.
     */
    private void ajouterConstantes(){
        // On commence à énumérer toutes les variables locales d'une fonction donc il faut reset par rapport à la précédente
        FabriqueIdentifiants.getInstance().resetCompteurVariableLocale();
        TDS tds = TDS.getInstance();

        // Le tableau s'ajoute tout seul.
        for (DeclarationConstante constante : constantes) {
            Entree entree = constante.getEntree();
            Symbole symbole = constante.getSymbole();

            tds.ajouter(numeroBloc, entree, symbole);
        }
    }

    /**
     * Ajoute les tableaux dans la TDS.
     */
    private void ajouterTableaux(){

    }

    /**
     * @return la place à réserver pour les constantes et les tableaux.
     */
    public int getPlaceAReserver(){
        int placeEntier = 4;
        int nbConstantes = constantes.size();
        int nbTableaux = tableaux.size();
        int tailleDescriptif = 2 ;
        return nbConstantes*placeEntier + nbTableaux*tailleDescriptif*placeEntier;

    }

}
