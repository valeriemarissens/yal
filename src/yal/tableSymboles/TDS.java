package yal.tableSymboles;

import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TDS {
    private static TDS instance = new TDS();
    private int cptDeplacement;
    private HashMap<Entree, Symbole> tableSymbolesPP;
    private ArrayList<HashMap<Entree, Symbole>> listeTDS;
    private Stack pile;

    private TDS(){
        tableSymbolesPP = new HashMap<>();
        listeTDS = new ArrayList<>();
        listeTDS.add(tableSymbolesPP);
        cptDeplacement = 0;
        pile = new Stack<Integer>();
        pile.push((int)0);
    }

    public static TDS getInstance(){
        return instance;
    }

    public void ajouter(Entree entree, Symbole symbole){
        if (!tableSymbolesPP.containsKey(entree)){
            symbole.setDeplacement(cptDeplacement);
            cptDeplacement -= 4;

            tableSymbolesPP.put(entree,symbole);
        }
        else{
            int ligneErreur = entree.getLigne();
            String messageExplicite = "la variable ou la fonction a déjà été déclarée.";
            MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,messageExplicite);
        }
    }

    public void ajouterNouvelleTDS(){
        HashMap<Entree, Symbole> donneesFonction = new HashMap<>();
        listeTDS.add(donneesFonction);
    }

    public void ajouterVariableLocale(int numeroBloc, Entree entree, Symbole symbole){
        int compteur = FabriqueIdentifiants.getInstance().getCompteurVariableLocale();
        ajouter(numeroBloc, entree, symbole, compteur);
    }

    /**
     * Fonction appelée par ajouterVariableLocale(...) ou ajouterParametre(...) pour la factorisation.
     * La seule différence entre les deux est le compteur.
     * Une variable locale ne peut pas avoir le même idf qu'un paramètre.
     * @param numeroBloc
     * @param entree
     * @param symbole
     * @param compteur
     */
    private void ajouter(int numeroBloc, Entree entree, Symbole symbole, int compteur){
        // Réservation de l'espace pour les variables locales aussi
        cptDeplacement -= 4;
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
            symbole = tableSymbolesPP.get(e);
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

    // Obsolète ?
    public int getTailleZoneVariable(){
        return cptDeplacement;
    }

    public String toString(int numeroBloc){
        return listeTDS.get(numeroBloc).toString();
    }
}
