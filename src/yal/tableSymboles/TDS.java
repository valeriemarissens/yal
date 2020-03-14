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
        HashMap<Entree, Symbole> donneesFonction = listeTDS.get(numeroBloc);
        int compteur = FabriqueIdentifiants.getInstance().getCompteurVariableLocale();

            if (!donneesFonction.containsKey(entree)){
                symbole.setDeplacement(compteur);
                symbole.setNumeroBloc(numeroBloc);
                donneesFonction.put(entree,symbole);
            }
            else{
                int ligneErreur = entree.getLigne();
                String messageExplicite = "la variable a déjà été déclarée.";
                MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,messageExplicite);
            }
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

    public static TDS getInstance(){
        return instance;
    }

    // Obsolète ?
    public int getTailleZoneVariable(){
        return cptDeplacement;
    }
}
