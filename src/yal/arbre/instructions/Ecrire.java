package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.outils.FabriqueIdentifiants;

public class Ecrire extends Instruction {
    protected Expression exp ;

    public Ecrire (Expression e, int n) {
        super(n) ;
        exp = e ;
    }

    @Override
    public void verifier() {
        exp.verifier();
    }


    /**
     * Si $v0 == 1 alors $v0 <- "vrai"
     * Sinon $v0 <- "faux"
     * @return code MIPS qui modifie le booléen de 1 ou 0 à "vrai" ou "faux"
     * et le garde dans $v0.
     */
    private String transformerBooleen(){
        int identifiant = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String nomEtiquetteSinon = "sinon"+identifiant;
        String nomEtiquetteFinsi = "finsi"+identifiant;

        StringBuilder code = new StringBuilder();

        code.append("\n");
        code.append("\t # Transformation du booléen en 'vrai' ou 'faux'\n");

        // On met 0 dans $t8.
        code.append("\t li $t8, 0 \n");

        // Si $v0 != 0 alors on va à l'étiquette Sinon.
        code.append("\t beq $v0, $t8, ");
        code.append(nomEtiquetteSinon);
        code.append("\n");

        // Ici $v0 == 0, donc on met "vrai" dans $v0
        // et on va a l'étiquette Finsi.
        code.append("\t la $v0, vrai \n");
        code.append("\t j ");
        code.append(nomEtiquetteFinsi);
        code.append("\n");

        // Étiquette Sinon :
        // Ici $v0 == 1, donc on met "faux" dans $v0
        code.append(nomEtiquetteSinon);
        code.append(": \n\t la $v0, faux \n");

        // Étiquette Finsi.
        code.append(nomEtiquetteFinsi);
        code.append(": \n\t # On continue... \n");
        code.append("\n");

        return code.toString();
    }

    /**
     * Le code d'écriture d'un string est 4 et celui d'un int est 1.
     * @return code d'écriture de exp suivie d'un retour à la ligne
     * (défini dans Programme.java).
     */
    @Override
    public String toMIPS() {
        String codeEcriture = "1";
        boolean estBooleen = exp.estBooleen();
        StringBuilder mips = new StringBuilder("\n\t # Ecrire "+exp.toString()+"\n");
        mips.append(exp.toMIPS());

        if (estBooleen){
            mips.append(transformerBooleen());
            codeEcriture = "4";
        }

        mips.append("\t move $a0, $v0 \n");
        mips.append("\t li $v0, "+codeEcriture+" \n");
        mips.append("\t syscall \n");
        mips.append("\t li $v0, 4 \n");
        mips.append("\t la $a0, retourLigne \n");
        mips.append("\t syscall \n");


        return mips.toString();
    }

}
