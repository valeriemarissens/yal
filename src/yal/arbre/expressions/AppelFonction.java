package yal.arbre.expressions;

import yal.arbre.EnsembleParametresAppel;
import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

public class AppelFonction extends Expression {
    String nom ;
    EntreeFonction entree;
    SymboleFonction symbole;
    EnsembleParametresAppel parametres;

    protected AppelFonction(int n) {
        super(n);
    }

    public AppelFonction(String nomFonction, EnsembleParametresAppel parametres, int noLigne){
        super(noLigne);
        nom = nomFonction;
        this.parametres = parametres;

        int nbParametresAppel = 0;
        if (parametres != null){
            nbParametresAppel = parametres.getNbParametresAppel();
        }

        entree = new EntreeFonction(nomFonction, noLigne, nbParametresAppel);
        //todo voir si ceci est important : répétition de chercherSymbole()
        //chercherSymbole();
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
        if (parametres != null){
            parametres.verifier();
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
        TDS.getInstance().entreeBloc(symbole.getNumBloc());

        StringBuilder mips = new StringBuilder();

        mips.append("\n");
        mips.append("\t # Début appel fonction "+nom+"(). \n");


        // On empile les valeurs des paramètres donnés.
        if (parametres != null) {
            parametres.reverse();
            mips.append("\t # Réservation de place pour les paramètres. \n ");
            mips.append("\t add $sp, $sp, -");
            mips.append(parametres.getNbParametresAppel()*4);
            mips.append("\n");
            mips.append("\t add $s3, $s3, -");
            mips.append(parametres.getNbParametresAppel()*4);
            mips.append("\n \n");

            mips.append("\t # On empile les paramètres de l'appel. \n");
            mips.append(toMIPSParametresAppel());
            mips.append("\n ");
        }

        // On stocke l'adresse où on est avec jal :
        // jal stocke l'adresse de l'instruction suivante dans $ra pour éviter les boucles.
        mips.append("\t jal ");
        mips.append(symbole.getNomEtiquette());
        mips.append("\n");

        // C'est DeclarationFonction qui s'occupe d'empiler $ra.
        return mips.toString();
    }

    /**
     * Il faut les empiler dans l'ordre inverse.
     * @return
     */
    private String toMIPSParametresAppel(){
        StringBuilder mips = new StringBuilder();
        for (Expression parametre : parametres){
            mips.append(parametre.toMIPS());
            mips.append("\t sw $v0, 0($s3) \n");
            mips.append("\t add $s3, $s3, -4 \n");
            mips.append("\n");
        }

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
