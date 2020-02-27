package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

// Quelque part : TDS.getInstance().entreeBloc(s.getNbBloc()) ;
// Quelque part après (à retourne ?): TDS.getInstance().sortieBloc() ;
public class AppelFonction extends Expression {
    String nom ;
    EntreeFonction entree;
    SymboleFonction symbole;

    protected AppelFonction(int n) {
        super(n);
    }

    public AppelFonction(String nomFonction, int noLigne){
        super(noLigne);
        nom = nomFonction;
        entree = new EntreeFonction(nomFonction, noLigne);
        chercherSymbole();
        estBooleen = false;
    }

    private void chercherSymbole() {
        symbole = (SymboleFonction)TDS.getInstance().identifier(entree);
    }

    /**
     * Vérifie que la fonction soit déclarée précédemment.
     */
    @Override
    public void verifier() {
        if (symbole == null){
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,"La fonction n'a pas été déclarée.");
        }
    }

    /**
     * La fonction donne la valeur de retour dans $v0.
     * La valeur de retour est gardée dans la pile ; c'est retourne qui s'occupe de revenir et de dépiler.
     *
     * On est censées stocker le n° de région (mais uniquement mais on aura des var locales p-ê).
     */
    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        // On stocke l'adresse où on est avec jal :
        // jal stocke l'adresse de l'instruction suivante dans $ra pour éviter les boucles.
        mips.append("\t jal ");
        mips.append(symbole.getNomEtiquette());
        mips.append("\n");

        // C'est DeclarationFonction qui s'occupe d'empiler $ra.
        return mips.toString();
    }

    @Override
    public String getType() {
        return "AppelFonction";
    }

    public String toString() {
        return nom + "()";
    }

}