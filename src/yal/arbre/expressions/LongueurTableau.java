package yal.arbre.expressions;

public class LongueurTableau extends AppelTableau {
    public LongueurTableau(String id, int n) {
        super(id, n);
    }

    public String toMIPS(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On va chercher l'adresse du descriptif du tableau, dont la valeur correspond à la taille du tableau. \n");
        mips.append("\t lw $v0, " + symbole.getDeplacement() + registre + "\n" );
        return mips.toString();
    }
}
