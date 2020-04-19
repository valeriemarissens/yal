package yal.tableSymboles;

import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TDS {
    private static TDS instance = new TDS();
    private int cptDeplacement;
    private HashMap<Entree, Symbole> tdsPP;
    private ArrayList<HashMap<Entree, Symbole>> listeTDS;
    private Stack pile;

    private TDS(){
        tdsPP = new HashMap<>();
        listeTDS = new ArrayList<>();
        listeTDS.add(tdsPP);
        cptDeplacement = 0;
        pile = new Stack<Integer>();
        pile.push((int)0);
    }

    public static TDS getInstance(){
        return instance;
    }

    /**
     * Ajoute une variable, tableau ou fonction dans une des TDS.
     * C'est la fonction générale pour tout ajout dans la TDS.
     *
     * @param numeroBloc : numéro du bloc où se trouve la variable ou fonction.
     * @param entree : entrée de la variable ou fonction.
     * @param symbole : symbole de la variable ou fonction.
     */
    public void ajouter(int numeroBloc, Entree entree, Symbole symbole){
        if (numeroBloc==0){
            ajouter(entree, symbole);
        }else{
            ajouterVariableLocale(numeroBloc, entree, symbole);
        }
    }

    /**
     * Ajout d'une variable dans la TDS du programme principal.
     *
     * @param entree
     * @param symbole
     */
    private void ajouter(Entree entree, Symbole symbole){
        if (!tdsPP.containsKey(entree)){
            symbole.setDeplacement(cptDeplacement);

            /* On n'augmente le nombre de place à réserver que si l'entrée est une variable
            (tableau ou constante). */
            if (!entree.estTableau()){
                cptDeplacement -= 4;

                /* Le tableau prend plus de place à cause de son descriptif. */
            }else{
                cptDeplacement -= 8;
            }

            tdsPP.put(entree,symbole);
        }
        else{
            int ligneErreur = entree.getLigne();
            String messageExplicite = "la variable ou la fonction a déjà été déclarée.";
            MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,messageExplicite);
        }
    }

    /**
     * Ajout d'une nouvelle TDS lors de la déclaration d'une fonction.
     */
    public void ajouterNouvelleTDS(){
        HashMap<Entree, Symbole> donneesFonction = new HashMap<>();
        listeTDS.add(donneesFonction);
    }

    private void ajouterVariableLocale(int numeroBloc, Entree entree, Symbole symbole){
        int compteur = FabriqueIdentifiants.getInstance().getCompteurVariableLocale();
        ajouter(numeroBloc, entree, symbole, compteur);
    }

    /**
     * Fonction appelée par ajouterVariableLocale(...) ou ajouterParametre(...) pour la factorisation.
     * La seule différence entre les deux est le compteur.
     * Une variable locale ne peut pas avoir le même idf qu'un paramètre.
     *
     * @param numeroBloc
     * @param entree
     * @param symbole
     * @param compteur
     */
    private void ajouter(int numeroBloc, Entree entree, Symbole symbole, int compteur){
        // Réservation de l'espace pour les variables locales aussi
        // TODO : Est-ce que ce cptDeplacement est utile ici puisqu'on soustrait sp dans les fonctions ???
        //cptDeplacement -= 4;
        HashMap<Entree, Symbole> donneesFonction = listeTDS.get(numeroBloc);
            if (!donneesFonction.containsKey(entree)){
                symbole.setDeplacement(compteur);
                symbole.setNumeroBloc(numeroBloc);
                donneesFonction.put(entree,symbole);
            }
            else{
                int ligneErreur = entree.getLigne();
                String messageExplicite = "la variable a déjà été déclarée en tant que paramètre ou en tant que variable locale.";
                MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,messageExplicite);
            }
        }

    public void ajouterParametre(int numeroBloc, Entree entree, Symbole symbole){
        int compteur = FabriqueIdentifiants.getInstance().getCompteurParametre();
        ajouter(numeroBloc, entree, symbole, compteur);
     }

    public Symbole identifier(Entree e){
        int numeroBloc = (int)pile.peek();
        HashMap<Entree, Symbole> donneesFonctionActuelle = listeTDS.get(numeroBloc);

        Symbole symbole = donneesFonctionActuelle.get(e);
        if (symbole==null){
            symbole = tdsPP.get(e);
        }
        return symbole;
    }

    /**
     * On empile le numéro de bloc dans lequel on est,
     * c'est le symbole qui le donne.
     */
    public void entreeBloc(int nbBloc){
        pile.push(nbBloc);
    }

    /**
     * Dépile : on retourne dans le bloc précédent.
     */
    public void sortieBloc(){
        int nbBloc = (int) pile.pop();
    }

    // TODO : Obsolète ?
    public int getTailleZoneVariable(){
        return cptDeplacement;
    }

    public String toString(int numeroBloc){
        return listeTDS.get(numeroBloc).toString();
    }

    public int getBlocCourant(){
        return (int)pile.peek();
    }
}
