package yal.arbre.expressions;

public class Superieur extends Calcul {

    public Superieur(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    @Override
    String titreOperation() {
        return "comparaison "+toString()+"\n";
    }

    /**
     * sgt = set greater than
     * Si e1 > e2 alors $v0 <- 1, sinon $v0 <- 0.
     * @return
     */
    @Override
    String calculOperation() {
        StringBuilder calcul = new StringBuilder();
        calcul.append("\t sgt $v0, $t8, $v0 \t\t # $v0 <- $t8 > $v0 \n");
        calcul.append("\t \n");
        return calcul.toString();
        // if $v0 == 1 then $v0 <- "vrai"
        // else $v0 <- "faux"

        /*"si : \n";
        "\t beqi $v0, 1, sinon \n";
        "\t lw $"*/
    }

    @Override
    String operateur() {
        return " > ";
    }
}
