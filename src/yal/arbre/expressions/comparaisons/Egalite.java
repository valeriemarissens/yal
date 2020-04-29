package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.Variable;
import yal.arbre.expressions.calculs.CalculBooleen;
import yal.outils.FabriqueIdentifiants;

public class Egalite extends CalculBooleen {
    private String operateur;
    private boolean difference = false;

    public Egalite(Expression e1, Expression e2, String op, int i){
        super(e1, e2, i);
        operateur = op;
        if (op.equals("!=")) difference = true;
    }

    public String titreOperation(){
        return " égalité entre deux expressions. \n";
    }

    @Override
    public String calculOperation() {
        StringBuilder code = new StringBuilder();

        /* Avec le vérifier, si l'exp gauche est un tableau alors l'exp droite aussi. */
        if (!expGauche.estTableau()){
            code.append(toMIPSConstantes());
        }else {
            code.append(toMIPSTableaux());
        }

        return code.toString();
    }

    private String toMIPSConstantes(){
        StringBuilder code = new StringBuilder();
        // On commence par soustraire les deux nombres :
        // $v0 = 0 si les nombres sont égaux
        code.append("\t subu $v0, $v0, $t8 \n"); // (subu au lieu de sub évite les overflow

        // $v0 devient 1 si les nombres ne sont pas égaux.
        code.append("\t sltu $v0, $0, $v0 \n");

        if (!difference) {
            // Devient 0 si c'est 1 et inversement
            code.append("\t xori $v0, $v0, 1 \n");
        }

        return code.toString();
    }

    private String toMIPSTableaux(){
        Variable tableauGauche = (Variable) expGauche;
        Variable tableauDroite = (Variable) expDroite;

        int numero = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String partieEtiquette = tableauGauche.toString()+"_"+tableauDroite.toString()+ "_" +numero;

        String etiquetteFin = "fin_egalite_"+partieEtiquette;
        String etiquetteComparerElements = "comparer_elements_"+partieEtiquette;
        String etiquetteInversement = "inverser_valeur"+partieEtiquette;

        StringBuilder mips = new StringBuilder();
        /* On doit commencer par vérifier si leurs tailles sont égales. */
        /* Leurs valeurs sont dans $t8 et $v0 (code de la super super classe Calcul). */

        // $v0 = 0 si les nombres sont égaux
        mips.append("\t subu $v0, $v0, $t8 \n");
        // $v0 devient 1 si les nombres ne sont pas égaux (réduit à 1 au lieu de 2, 3, 4...).
        mips.append("\t sltu $v0, $0, $v0 \n");

        /* Si on a demandé une égalité : */
        if (!difference) {
            // Si $v0 > 0, alors les tailles de tableaux ne sont pas égales, donc les tableaux ne sont pas égaux.
            // Donc pas besoin de comparer les éléments. Sinon, on ne passe pas par l'étiquette et on les compare.
            mips.append("\t bgtz $v0, "+ etiquetteInversement + "\n\n");
        }else {
            // Si $v0 == 0, alors les tailles de tableaux sont égales, donc il faut aller vérifier les éléments aussi.
            // Si $v0 > 0, alors on j à l'étiquette de fin sans avoir besoin de changer sa valeur.
            mips.append("\t bgtz $v0, " + etiquetteFin + "\n\n");
        }
        /* Puis on vérifie si tous leurs éléments sont identiques. */
        mips.append(etiquetteComparerElements);
        mips.append(" : \n");
        mips.append(toMIPSComparerElements(etiquetteFin, etiquetteInversement, numero, tableauGauche.getDeplacement(), tableauDroite.getDeplacement(), tableauGauche.getRegistre(), tableauDroite.getRegistre()));
        mips.append("\n");

        mips.append(etiquetteInversement);
        mips.append(" : \n \t xori $v0, $v0, 1 \n\n");
        mips.append(etiquetteFin);
        mips.append(" : \n \n" );

        return mips.toString();
    }

    private String toMIPSComparerElements(String etiquetteFin, String etiquetteInversement,  int numero, int deplacementGauche, int deplacementDroite, String registreGauche, String registreDroite){
        /* À ce moment là, $t8 contient le nombre d'éléments des tableaux. */
        String etiquetteBoucle = "boucle_egTableaux_"+numero;

        StringBuilder mips = new StringBuilder();
        mips.append("\t # On charge dans $t3 l'@OV du tableau de gauche et dans $t7 l'@OV du tableau de droite. \n");
        mips.append("\t la $t3, " + (deplacementGauche-4) + registreGauche + "\n" );
        mips.append("\t la $t7, " + (deplacementDroite-4) + registreDroite + "\n");
        mips.append("\n");

        mips.append(etiquetteBoucle+ ": \n");

        /* Si $t8 est égal à 0, on quitte. */
        mips.append("\t blez $t8,"+ etiquetteFin+ "\n");
        mips.append("\n");

        /* On utilise $v0 et $v1 pour stocker les valeurs des tableaux. */
        mips.append("\t lw $v0, 4($t3) \n");
        mips.append("\t lw $v1, 4($t7) \n");

        mips.append("\t subu $v0, $v0, $v1 \n");
        mips.append("\t sltu $v0, $0, $v0 \n");

        if (!difference){
            mips.append("\t bgtz $v0, "+ etiquetteFin + "\n");
        }else {
            mips.append("\t bgtz $v0, "+ etiquetteInversement + "\n");
        }
        
        mips.append("\n");
        /* On va chercher l'élément suivant. */
        mips.append("\t addi $t3, $t3, -4 \n");
        mips.append("\t addi $t7, $t7, -4 \n");

        /* -1 à la boucle. */
        mips.append("\t sub $t8, $t8, 1 \n");
        mips.append("\n");

        /* On continue la boucle. */
        mips.append("\t j ");
        mips.append(etiquetteBoucle);
        mips.append(" \n");
        mips.append("\n");

        mips.append("\t j "+ etiquetteFin + "\n");
        return mips.toString();
    }

    @Override
    public String toString(){
        return expGauche.toString()+" "+operateur+" "+expDroite;
    }

    @Override
    public String getOperateur() {
        return operateur;
    }
}
