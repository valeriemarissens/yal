package yal.arbre.expressions;

import yal.arbre.instructions.Affectation;
import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

// Quelque part : TDS.getInstance().entreeBloc(s.getNbBloc()) ; => plutôt dans DeclarationFonction ? (CM pg 16)
// Quelque part après (à retourne ?): TDS.getInstance().sortieBloc() ;
public class AppelFonction extends Expression {
    String nom ;
    EntreeFonction entree;
    SymboleFonction symbole;
    Expression parametre;
    Affectation affectationParametre;

    protected AppelFonction(int n) {
        super(n);
    }

    public AppelFonction(String nomFonction, Expression p, int noLigne){
        super(noLigne);
        nom = nomFonction;
        parametre = p;

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
        if (parametre != null){
            parametre.verifier();
        }

        chercherSymbole();
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

        if (parametre != null) {
            String idParam = symbole.getIdParametre();
            affectationParametre = new Affectation(idParam, parametre, noLigne + 1);

            //On affecte le(s) paramètre(s) :
            mips.append("\t # On affecte le(s) paramètre(s) \n");
            mips.append("\t ");
            mips.append(affectationParametre.toMIPS());
            mips.append("\n");
        }

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
