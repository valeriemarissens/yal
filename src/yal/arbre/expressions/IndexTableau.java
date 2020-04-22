package yal.arbre.expressions;

import yal.exceptions.MessagesErreursSemantiques;

public class IndexTableau extends AppelTableau {
    Expression expression;

    /**
     * Classe qui donne la valeur d'un index (dans $v0) ou son adresse (dans $t3).
     *
     * @param idf : nom du tableau
     * @param noLigne : ligne de l'instruction
     * @param expression : index du tableau
     */
    public IndexTableau(String idf, int noLigne, Expression expression) {
        super(idf, noLigne);
        this.expression = expression;
    }

    /**
     * Vérifie si le tableau existe et si l'expression est correcte.
     * C'est verifierToMIPS() qui s'occupe de vérifier si la valeur de l'expression est
     * correcte (car n'est connue qu'à l'exécution).
     */
    public void verifier(){
        super.verifier();
        expression.verifier();
    }

    /**
     * Vérifie si l'expression donnée pour l'index est dans les bornes du tableau.
     * Cette valeur n'est connue qu'à l'exécution d'où la génération de la vérification en MIPS.
     */
    public void verifierToMIPS(){
    }

    /**
     * Génère un code MIPS qui donne l'adresse de l'index demandé dans $t3.
     * (Fonction utilisée pour affecter une valeur à cet index.)
     * @return le code MIPS laissant une adresse dans $t3.
     */
    public String toMIPSIndex(){
        StringBuilder mips = new StringBuilder();
        mips.append("\t # On place la valeur de notre index dans $v0. \n");
        mips.append(expression.toMIPS());

        // L'adresse de la première valeur du tableau est dans $t3.
        mips.append(toMIPSDebutTableau());
        mips.append("\t # On multiplie notre valeur d'index par 4 (taille d'un int). \n");
        mips.append("\t li $t8, 4 \n");
        mips.append("\t mult $t8, $v0 \n");
        mips.append("\t mflo $v0 \n");
        mips.append("\t # On retire la valeur de l'index à $t3 (= notre début de tableau). \n" );
        mips.append("\t sub $t3, $t3, $v0 \n");

        return mips.toString();
    }

    /**
     * Génère un code MIPS qui donne à $v0 la valeur de l'index demandé du tableau.
     * (Fonction utilisée par l'instruction Ecrire ou pour l'affectation.)
     * @return le code MIPS avec la valeur demandée dans $v0.
     */
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

