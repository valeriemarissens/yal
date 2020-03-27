package yal.arbre;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.Entree;
import yal.tableSymboles.EntreeVariable;
import yal.tableSymboles.SymboleVariable;
import yal.tableSymboles.TDS;

public class DeclarationTableau extends ArbreAbstrait {
    private Expression taille;
    private String registre;
    private boolean estVariableLocale;
    private String idf;

    public DeclarationTableau(String idf, Expression e, int numeroBloc, int n) {
        super(n);
        this.idf = idf;
        this.taille = e;
        if (numeroBloc==0){
            registre="s7";
            estVariableLocale = false;
        }else{
            registre="s2";
            estVariableLocale = true;
        }

        /* On enregistre le tableau comme une variable normale dans la TDS.*/
        Entree entree = new EntreeVariable(idf, n) ;
        SymboleVariable symbole = new SymboleVariable();

        /* Selon si ce tableau est une variable locale ou non, la fonction utilisée diffère. */
        if (!estVariableLocale){
            TDS.getInstance().ajouter(entree, symbole);
        }else {
            TDS.getInstance().ajouterVariableLocale(numeroBloc, entree, symbole);
        }

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
        StringBuilder mips = new StringBuilder();
        mips.append("\t # Déclaration du tableau "+ idf + "\n");

        // Vérification de la taille du tableau :
        /* Si > 0, alors OK et $v0 contient la taille de ce tableau. */
        /* Sinon, jump à fin de programme ("end :"). */
        mips.append(verificationTailleToMIPS());

        mips.append("\t # On réserve la place pour le descriptif. \n");
        mips.append("\t addi $sp, $sp, -12 \n");
        mips.append("\n");

        // Descriptif :
        mips.append("\t # On empile la taille du tableau. \n");
        mips.append(taille.toMIPS());
        mips.append("\t sw $v0, 0($"+registre+") \n");
        mips.append("\t addi $" + registre + ", $"+ registre +", -4 \n");
        mips.append("\n");

        mips.append("\t # On pré-empile l'adresse de l'origine virtuelle. \n");
        mips.append("\t addi $" + registre + ", $"+ registre +", -4 \n");
        mips.append("\n");

        mips.append("\t # On cherche l'adresse actuelle de sp car c'est le début de notre tableau.");
        mips.append("\t la $t3, 0($sp) \n");

        mips.append("\t # On sauvegarde l'adresse à l'emplacement de l'@OV du descriptif. ");
        mips.append("\t sw $t3, 4($"+registre+") \n");

        mips.append("\t # On empile le descriptif dans la zone de variables locales.");

        // Élements du tableau :
        int deplacementTaille = 4 * 2;
        mips.append("\t # On réserve la place pour les éléments du tableau dans $sp \n");
        mips.append("\t addi $sp, $sp, -"+ deplacementTaille +" \n");

        /* On initialise les éléments du tableau à 0. */
        mips.append(elementsTableauToMIPS());

        return mips.toString();
    }

    private String verificationTailleToMIPS(){
        int identifiant = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String nomEtiquetteSinon = "sinon_tableau_"+identifiant;
        String nomEtiquetteFinsi = "finsi_tableau_"+identifiant;

        StringBuilder mips = new StringBuilder();

        mips.append("\n");
        mips.append("\t # On vérifie que taille > 0. \n");
        mips.append(taille.toMIPS());
        mips.append("\t move $t8, $v0 \n");

        // Dans ce cas, taille < 0, donc on va au Sinon :
        mips.append("\t slt $v0, $t8, $v0 \n");
        mips.append("\t beq $v0, $0, ");
        mips.append(nomEtiquetteSinon);
        mips.append("\n");

        // Ici taille > 0, donc tout va bien :
        mips.append("\t j ");
        mips.append(nomEtiquetteFinsi);
        mips.append("\n");
        mips.append("\n");

        // Étiquette Sinon :
        mips.append(nomEtiquetteSinon);
        mips.append(": \n\t la $v0, tailleNegative \n");
        mips.append("\n");

        // Écrit l'erreur :
        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, 4 \n");
        mips.append("\t syscall \n");
        mips.append("\n");

        mips.append("\t li $v0, 4 \n");
        mips.append("\t la $a0, retourLigne \n");
        mips.append("\t syscall \n");
        mips.append("\n");

        // On va à la fin pour arrêter l'exécution :
        mips.append("\t j end \n");
        mips.append("\n");

        // Étiquette Finsi :
        mips.append(nomEtiquetteFinsi);
        mips.append(": \n\t # On continue... \n");
        mips.append("\n");

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
    private String elementsTableauToMIPS(){
        StringBuilder mips = new StringBuilder();
        mips.append("\n");
        mips.append("\t # On empile les éléments du tableau dans $sp. \n");

        String nomEtiquette = "boucle_initialisation_tableau_"+idf;
        // Boucle :
        /* Tant que $v0 n'est pas égal à 0 ...*/
        mips.append(nomEtiquette);
        mips.append(" : \n");

        /* On range 0 dans $t8. */
        /* Il sert en tant que sortie de boucle et en tant que valeur d'initialisation. */
        mips.append("\t li $t8, 0 \n");

        /* Si $v0 est égal à 0, on quitte. */
        mips.append("\t beq $v0, $t8, end \n");
        mips.append("\n");

        /* On range $t8 dans $t3, qui est l'emplacement du i-eme élément du tableau. */
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
