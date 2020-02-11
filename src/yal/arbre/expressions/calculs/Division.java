package yal.arbre.expressions.calculs;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

public class Division extends Calcul {

    public Division(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    /**
     * Vérifie que l'expression de droite ne soit
     * pas égale à 0.
     */
    @Override
    public void verifier(){
        Expression expDroite = getExpDroite();
        String typeExpDroite = expDroite.getType();
        if (typeExpDroite.equals("ConstanteEntiere")){
            ConstanteEntiere constanteEntiere = (ConstanteEntiere) expDroite;
            if (constanteEntiere.toString().equals("0")){
                MessagesErreursSemantiques.getInstance().ajouter(noLigne,"La division par 0 est interdite.");
            }
        }
    }

    @Override
    public String titreOperation() {
        return "division "+toString()+"\n";
    }

    @Override
    public String calculOperation() {
        int identifiant = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String nomEtiquetteSinon = "sinon"+identifiant;
        String nomEtiquetteFinsi = "finsi"+identifiant;

        StringBuilder calcul = new StringBuilder();

        calcul.append("\t # Vérifie division par 0 \n");

        // Avant calculOperation, la classe Calcul a mis expDroite dans $v0
        // Si $v0 = 0 alors on va à l'étiquette Sinon :
        calcul.append("\t beq $v0, $0, ");
        calcul.append(nomEtiquetteSinon);
        calcul.append("\n");

        // Ici $v0 != 0 donc on peut procéder à la division :
        calcul.append("\t div $t8, $v0 \t\t # $t8 / $v0 \n");
        calcul.append("\t mflo $v0  \t\t# on range le quotien dans $v0 \n");

        // Et on va à l'étiquette Finsi :
        calcul.append("\t j ");
        calcul.append(nomEtiquetteFinsi);
        calcul.append("\n");

        // Étiquette Sinon :
        // Ici $v0 == 0, donc on ne peut pas faire la division :
        calcul.append(nomEtiquetteSinon);
        calcul.append(": \n\t la $v0, divisionZero \n");

        // Écrit l'erreur :
        calcul.append("\t move $a0, $v0 \n");
        calcul.append("\t li $v0, 4 \n");
        calcul.append("\t syscall \n");
        calcul.append("\t li $v0, 4 \n");
        calcul.append("\t la $a0, retourLigne \n");
        calcul.append("\t syscall \n");

        // On va à la fin pour arrêter l'exécution :
        calcul.append("\t j end \n");

        // Étiquette Finsi :
        calcul.append(nomEtiquetteFinsi);
        calcul.append(": \n\t # On continue... \n");
        calcul.append("\n");

        return calcul.toString();
    }

    @Override
    public String getOperateur() {
        return "/";
    }
}
