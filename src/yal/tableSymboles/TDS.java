package yal.tableSymboles;

import yal.exceptions.MessagesErreursSemantiques;
import java.util.HashMap;
import java.util.Stack;

public class TDS {
    private static TDS instance = new TDS();
    private int cptDeplacement;
    private HashMap<Entree, Symbole> tableSymboles;
    private Stack pile;

    private TDS(){
        tableSymboles = new HashMap<>();
        cptDeplacement = 0;
        pile = new Stack<Integer>();
    }

    public void ajouter(Entree entree, Symbole symbole){
        if (!tableSymboles.containsKey(entree)){
            symbole.setDeplacement(cptDeplacement);
            cptDeplacement -= 4;

            /* C'est pas à l'appel de fonction ça ?
            if (entree.estFonction()) {
                if (pile.empty()) {
                    symbole.setNbBloc(0);
                }
                else {
                    int nbBlocActuel = (int) pile.peek();
                    symbole.setNbBloc(nbBlocActuel + 1);
                }
            }
    */
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
    }

    /**
     * Dépile : on retourne dans le bloc précédent.
     */
    public void sortieBloc(){
        pile.pop();
    }

    public static TDS getInstance(){
        return instance;
    }

    // Obsolète ?
    public int getTailleZoneVariable(){
        return cptDeplacement;
    }
}
