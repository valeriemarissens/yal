package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

public class Et extends Expression {
    private Expression expressionGauche;
    private Expression expressionDroite;

    public Et(Expression e1, Expression e2, int i) {
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;
    }

    @Override
    public void verifier() {
        expressionGauche.verifier();
        expressionDroite.verifier();

        boolean pasBooleenGauche = expressionGauche.getType().equals("Calcul");
        boolean pasBooleenDroite = expressionDroite.getType().equals("Calcul");
        if (pasBooleenGauche || pasBooleenDroite){
            String messageExplicite = "Les opérandes de 'et', 'oui' et 'non' doivent être des booléens et pas des entiers.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,messageExplicite);
        }
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

        // On met 0 dans $t8.
        code.append("\t li $t4, 0 \n");

        // Si $v0 != 0 alors on va à l'étiquette Sinon.
        code.append("\t beq $v0, $t4, ");
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
        code.append(": \n\t #On continue... \n");

        return code.toString();
    }

    @Override
    public String toMIPS() {
        StringBuilder code = new StringBuilder();
        // Le résultat de expressionDroite est gardé dans $v0.
        code.append(expressionDroite.toMIPS());

        // Mais on aura besoin de $v0 pour expressionGauche, alors on le met dans $t4.
        code.append("\t move $t4, $v0 \n");

        // Maintenant, on garde le résultat de expressionGauche dans $v0.
        code.append(expressionGauche.toMIPS());

        // Et on peut faire la comparaison.
        code.append("\t");
        code.append("and $v0, $v0, $t4");
        code.append("\n\n");

        code.append(transformerBooleen());

        return code.toString();
    }

    @Override
    public String getType() {
        return "Et";
    }

    @Override
    public String toString(){
        return expressionGauche.toString()+" et "+expressionDroite.toString();
    }
}
