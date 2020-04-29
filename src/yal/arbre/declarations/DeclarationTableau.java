package yal.arbre.declarations;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.arbre.instructions.Affectation;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;

public class DeclarationTableau extends Declaration {
    private Expression taille;
    private String registre;
    private boolean estVariableLocale;

    public DeclarationTableau(String idf, Expression e, int n) {
        super(idf, n);
        this.taille = e;

        registre="($s7)";
        estVariableLocale = false;

        entree = new EntreeVariable(idf, n) ;
        symbole = new SymboleVariable();

        // L'entrée précise que c'est un tableau pour le compteur (en effet, un tableau prend 2 places au lieu
        // d'une).
        ((EntreeVariable) entree).setEstTableau(true);
        ((SymboleVariable) symbole).setEstTableau(true);
    }

    @Override
    public void verifier() {

        /* Si ce n'est pas une variable locale, l'expression du tableau est une constante. */
        if (!estVariableLocale){
            String typeExpression = taille.getType();
            if (!typeExpression.equals("ConstanteEntiere")){
                MessagesErreursSemantiques.
                        getInstance().
                        ajouter(noLigne,
                                "L'expression d'un tableau du programme principal doit être une constante.");
            }

            /* Si c'est bien une constante, alors elle doit être supérieure à 0. */
            else {
                int valeurConstante = ((ConstanteEntiere) taille).getValeur();
                if (valeurConstante<1){
                    MessagesErreursSemantiques.
                            getInstance().
                            ajouter(noLigne,"L'expression d'un tableau du programme principal doit être une constante strictement positive.");
                }
            }
        }

    }

    public void setNumeroBloc(int numeroBloc){
        if (numeroBloc==0){
            registre="($s7)";
            estVariableLocale = false;
        }else{
            registre="($s2)";
            estVariableLocale = true;
        }
    }

    /**
     * Lors de la déclaration d'un tableau, il faut d'abord vérifier que la taille
     * du tableau soit bien strictement positive.
     *
     * On réserve la place pour le descriptif du tableau (de taille 4*2 car
     * il possède l'adresse de l'origine virtuelle et la taille du tableau).
     * On n'a pas pris en compte l'enjambée puisque, comme on ne gère que les tableaux
     * à une seule dimension, l'enjambée est égale à la taille d'un élément. De plus,
     * les éléments ne peuvent être que des entiers, donc ça ne sert à rien de l'empiler.
     *
     * Ensuite, on réserve la place dans $sp pour les éléments du tableau.
     * Puis on empile les éléments du tableau (tous initialisés à 0).
     */
    @Override
    public String toMIPS() {
        int deplacement = symbole.getDeplacement();
        StringBuilder mips = new StringBuilder();
        mips.append("\t # Déclaration du tableau "+ idf + "\n");

        // Vérification de la taille du tableau :
        /* Si > 0, alors OK et $v0 contient la taille de ce tableau. */
        /* Sinon, jump à fin de programme ("end :"). */
        mips.append(verificationTailleToMIPS());

        // Descriptif :
        mips.append("\t # On sauvegarde la taille du tableau. \n");

        /* La taille est maintenant dans $v0 */
        mips.append(taille.toMIPS());
        mips.append("\t sw $v0, "+ deplacement + registre + "\n");

        mips.append("\n");

        mips.append("\t # On cherche l'adresse actuelle de sp car c'est le début de notre tableau. \n");
        mips.append("\t move $t3, $sp \n");

        mips.append("\n");

        mips.append("\t # On sauvegarde l'adresse à l'emplacement de l'@OV du descriptif. \n");
        mips.append("\t sw $t3, " + (deplacement - 4) + registre+" \n");


        // Élements du tableau :

        /* Calcul en MIPS de la taille des éléments du tableau. */
        mips.append("\t # On calcule la place à laisser dans $sp pour les éléments (4 par élément). \n");
        mips.append("\t li $t8, 4 \n");
        mips.append("\t mult $t8, $v0  \n");
        mips.append("\t mflo $v0 \n");
        mips.append("\n");
        mips.append("\t # On réserve la place dans $sp. \n");
        mips.append("\t sub $sp, $sp, $v0 \n");

        /* On remet dans $v0 la taille du tableau. */
        mips.append(taille.toMIPS());

        mips.append("\n");
        /* On initialise les éléments du tableau à 0. */
        mips.append(initialisationTableau());

        return mips.toString();
    }

    private String verificationTailleToMIPS(){
        StringBuilder mips = new StringBuilder();

        mips.append("\n");
        mips.append("\t # On vérifie que taille > 0. \n");
        mips.append(taille.toMIPS());

        mips.append("\t blez $v0, ");
        mips.append("erreur_tailleTableau");
        mips.append("\n\n");

        return mips.toString();
    }

    /**
     * Les éléments du tableau sont rangés au sommet de la pile
     * et non pas dans la zone des variables locales.
     *
     * A ce moment là, $v0 contient encore la taille du tableau donc on va s'en servir.
     * $v0 > 0 (vérification faite avant, grâce à vérificationTailleToMIPS().
     * A ce moment là, $t3 contient encore l'adresse de début du tableau.
     *
     * Cette fonction doit se faire en MIPS car seul $v0 connait la taille du tableau.
     *
     * /!\ $v0  = 0 à la fin de cette fonction.
     */
    private String initialisationTableau(){
        String nomEtiquette = "boucle_initialisation_tableau_"+idf;
        String nomEtiquetteFin = "fin_"+nomEtiquette;
        StringBuilder mips = new StringBuilder();
        mips.append("\n");
        mips.append("\t # On empile les éléments du tableau dans $sp. \n");

        // Boucle :
        /* Tant que $v0 n'est pas égal à 0 ... ($v0 est la taille du tableau) */
        mips.append(nomEtiquette);
        mips.append(" : \n");

        /* On range 0 dans $t8. */
        /* Il sert en tant que sortie de boucle et en tant que valeur d'initialisation. */
        mips.append("\t li $t8, 0 \n");

        /* Si $v0 est égal à 0, on quitte. */
        mips.append("\t beq $v0, $t8,"+ nomEtiquetteFin + "\n");
        mips.append("\n");

        /* On range $t8 dans $t3, qui est l'emplacement du i-eme élément du tableau. */
        /* Petit souci avec tableau[0], d'où le 4($t3) qui a réglé le beug, à voir si le dernier élément est ok du coup... */
        mips.append("\t sw $t8, 0($t3) \n");

        /* On va chercher l'élément suivant. */
        mips.append("\t addi $t3, $t3, -4 \n");

        /* On soustrait 1 à $v0 pour la boucle. */
        mips.append("\t sub $v0, $v0, 1 \n");
        mips.append("\n");

        /* On continue la boucle. */
        mips.append("\t j ");
        mips.append(nomEtiquette);
        mips.append(" \n");
        mips.append("\n");

        mips.append(nomEtiquetteFin + ": \n\n");


        return mips.toString();
    }

    @Override
    public String getType() {
        return "DeclarationTableau";
    }

    @Override
    public boolean contientRetourne() {
        return false;
    }
}
