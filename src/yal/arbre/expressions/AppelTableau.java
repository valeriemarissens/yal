package yal.arbre.expressions;

public abstract class AppelTableau extends Variable {

    /**
     * Classe abstraite servant à une meilleure compréhension.
     * Ses enfants sont IndexTableau et LongueurTableau.
     *
     * @param idf : nom du tableau
     * @param noLigne : ligne de l'instruction
     */
    public AppelTableau(String idf, int noLigne) {
        super(idf, noLigne);
    }

    /**
     * Vérifie si le tableau a été déclaré.
     * Donne son registre et son symbole à cette classe.
     */
    @Override
    public void verifier() {
        chercherSymbole();
        super.verifier();
    }

    /**
     * Donne à $t3 l'adresse du premier élément du tableau.
     * @return le code MIPS laissant une adresse à $t3.
     */
    public String toMIPSDebutTableau(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On va chercher l'adresse de la première valeur du tableau. \n");
        mips.append("\t lw $t3, " + (symbole.getDeplacement()-4) + registre + "\n" );
        return mips.toString();
    }
}
