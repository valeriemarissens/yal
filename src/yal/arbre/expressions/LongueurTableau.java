package yal.arbre.expressions;

public class LongueurTableau extends AppelTableau {
    /**
     * Classe donnant la longueur d'un tableau.
     *
     * @param idf : nom du tableau
     * @param noLigne : ligne de l'instruction
     */
    public LongueurTableau(String idf, int noLigne) {
        super(idf, noLigne);
    }

    /**
     * Donne à $v0 la longueur du tableau.
     * @return le code MIPS avec la valeur demandée dans $v0.
     */
    public String toMIPS(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On va chercher l'adresse du descriptif du tableau, dont la valeur correspond à la taille du tableau. \n");
        mips.append("\t lw $v0, " + symbole.getDeplacement() + registre + "\n" );
        return mips.toString();
    }
}