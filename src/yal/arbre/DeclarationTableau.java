package yal.arbre;

import yal.arbre.expressions.Expression;
import yal.outils.FabriqueIdentifiants;

public class DeclarationTableau extends ArbreAbstrait {
    private Expression taille;

    public DeclarationTableau(String id, Expression e, int n) {
        super(n);
        this.taille = e;
    }

    @Override
    public void verifier() {
        //TODO: est-ce qu'on vérifie aussi ici la taille ?
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
        mips.append("\t # Déclaration du tableau \n");

        // Vérification de la taille du tableau :
        mips.append(verificationTailleToMIPS());

        mips.append("\t # On réserve la place pour le descriptif. \n");
        mips.append("\t addi $sp, $sp, -12 \n");
        mips.append("\n");

        // Descriptif :
        mips.append("\t # On empile la taille du tableau. \n");
        mips.append(taille.toMIPS());
        mips.append("\t sw $v0, 0($sp) \n");
        mips.append("\t addi $sp, $sp, -4 \n");
        mips.append("\n");

        mips.append("\t # On empile l'adresse de l'origine virtuelle. \n");
        mips.append("\t sw $s2, 0($sp) \n");
        mips.append("\t addi $sp, $sp, -4 \n");
        mips.append("\n");

        // Élements du tableau :
        int deplacementTaille = 4 * 2;
        mips.append("\t # On réserve la place pour les éléments du tableau dans $sp \n");
        mips.append("\t lw $v0, "+deplacementTaille+"($sp) \n");
        mips.append("\t addi $sp, $sp, $v0 \n");

        mips.append(elementsTableauToMIPS());

        return mips.toString();
    }

    private String verificationTailleToMIPS(){
        int identifiant = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String nomEtiquetteSinon = "sinon"+identifiant;
        String nomEtiquetteFinsi = "finsi"+identifiant;

        StringBuilder mips = new StringBuilder();

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

        // Étiquette Sinon :
        mips.append(nomEtiquetteSinon);
        mips.append(": \n\t la $v0, tailleNegative \n");

        // Écrit l'erreur :
        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, 4 \n");
        mips.append("\t syscall \n");
        mips.append("\t li $v0, 4 \n");
        mips.append("\t la $a0, retourLigne \n");
        mips.append("\t syscall \n");

        // On va à la fin pour arrêter l'exécution :
        mips.append("\t j end \n");

        // Étiquette Finsi :
        mips.append(nomEtiquetteFinsi);
        mips.append(": \n\t # On continue... \n");
        mips.append("\n");

        return mips.toString();
    }

    /**
     * Les éléments du tableau sont rangés au sommet de la pile
     * et non pas dans la zone des variables locales.
     */
    private String elementsTableauToMIPS(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On empile les éléments du tableau dans $sp \n");

        // Boucle
        mips.append("\t \n");
        mips.append("\t \n");
        mips.append("\t \n");
        mips.append("\t \n");
        mips.append("\t \n");
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
