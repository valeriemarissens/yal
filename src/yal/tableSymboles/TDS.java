package yal.tableSymboles;

import yal.exceptions.MessagesErreursSemantiques;
import java.util.HashMap;

public class TDS {
    private static TDS instance = new TDS();
    private int cptDeplacement;
    private HashMap<Entree, Symbole> tableSymboles;

    private TDS(){
        tableSymboles = new HashMap<>();
        cptDeplacement = 0;
    }

    public void ajouter(Entree entree, Symbole symbole){
        if (!tableSymboles.containsKey(entree)){
            symbole.setDeplacement(cptDeplacement);
            cptDeplacement -= 4;
            tableSymboles.put(entree,symbole);
        }
        else{
            tableSymboles.remove(entree);
            int ligneErreur = entree.getLigne();
            MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,"la variable a déjà été déclarée.");
        }
    }

    public Symbole identifier(Entree e){
        return tableSymboles.get(e);
    }

    public static TDS getInstance(){
        return instance;
    }

    public int getTailleZoneVariable(){
        return cptDeplacement;
    }
}
