package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;

public class IndexTableau extends AppelTableau {
    Expression expression;

    public IndexTableau(String idf, int noLigne, Expression expression) {
        super(idf, noLigne);
        this.expression = expression;
    }


    public void verifier(){
        super.verifier();
        expression.verifier();
    }

    /** Le premier index du tableau est 0 et sa taille n'est connue que dans le code MIPS. */
    public void verifierToMips(){
    }

    public String toMIPSIndex(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On place la valeur de notre index dans $v0. \n");
        mips.append(expression.toMIPS());

        // TODO : la valeur de l'index est dans $v0, il faut trouver le tableau mnt

        // L'adresse de la première valeur du tableau est dans $t3.
        mips.append(toMIPSDebutTableau());
        mips.append("\t # On multiplie notre valeur d'index par 4 (taille d'un int). \n");
        mips.append("\t li $t8, 4 \n");
        mips.append("\t mult $t8, $v0 \n");
        mips.append("\t mflo $v0 \n");
        mips.append("\t # On ajoute la valeur de l'index à $t3 (= notre début de tableau). \n" );
        mips.append("\t add $t3, $t3, $v0 \n");

        return mips.toString();
    }

    public String toMIPS(){
        StringBuilder mips = new StringBuilder();
        mips.append(toMIPSIndex());
        mips.append("\t lw $v0, 0($t3) \n");

        mips.append("\t # La valeur de l'index demandé du tableau est maintenant dans $v0.\n");
        mips.append("\n");
        return mips.toString();
    }


    public String getType(){
        return "IndexTableau";
    }
}


