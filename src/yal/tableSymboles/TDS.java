package yal.tableSymboles;

import yal.exceptions.MessagesErreursSemantiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class TDS {
    private static TDS instance = new TDS();
    private int cptDeplacement;
    private HashMap<Entree, Symbole> tableSymboles;
    private Stack pile;
    private HashMap<Entree, ArrayList<Symbole>> nouvelleTableSymboles;

    private TDS(){
        tableSymboles = new HashMap<>();
        cptDeplacement = 0;
        pile = new Stack<Integer>();
        nouvelleTableSymboles = new HashMap<>();
    }

    public void ajouter(Entree entree, Symbole symbole){
        if (!tableSymboles.containsKey(entree)){
            symbole.setDeplacement(cptDeplacement);
            cptDeplacement -= 4;

            tableSymboles.put(entree,symbole);
        }
        else{
            int ligneErreur = entree.getLigne();
            MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,"la variable ou la fonction a déjà été déclarée.");
        }
    }

    public Symbole identifier(Entree e){
        return tableSymboles.get(e);
    }

    /**
     * On empile le numéro de bloc dans lequel on est,
     * c'est le symbole qui le donne.
     */
    public void entreeBloc(int nbBloc){
        pile.push(nbBloc);
        System.out.println("ENTRÉE DANS BLOC "+nbBloc);
        System.out.println();
    }

    /**
     * Dépile : on retourne dans le bloc précédent.
     */
    public void sortieBloc(){
        int nbBloc = (int) pile.pop();
        System.out.println("SORTIE DU BLOC "+nbBloc);
        System.out.println();
    }

    public static TDS getInstance(){
        return instance;
    }

    // Obsolète ?
    public int getTailleZoneVariable(){
        return cptDeplacement;
    }

    public boolean isEmptyPile(){
        return pile.isEmpty();
    }

    public int peekPile(){
        return (int) pile.peek();
    }
}
