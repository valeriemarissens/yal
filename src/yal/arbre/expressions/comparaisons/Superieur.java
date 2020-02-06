package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.Constante;
import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.Soustraction;
import yal.arbre.expressions.comparaisons.Comparaison;

public class Superieur extends Comparaison {

    public Superieur(Expression e1, Expression e2, int i) {
        super(e1, e2, i);
    }

    String titreOperation() {
        return "comparaison "+toString()+"\n";
    }

    /**
     * Cette comparaison est faite par simple soustraction.
     * La soustraction, prise de la classe Soustraction, garde la valeur dans $v0,
     * comme pour les autres comparaisons.
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *              A utiliser pour utiliser le beq ensuite.
     */
    String toStringComparaisonCstEtCst(){
        StringBuilder calcul = new StringBuilder();
        System.out.println("on passe par toStringComparaisonCsteEtCste de Superieur");
        calcul.append("# Début de comparaison entre deux entiers. \n ");
        Soustraction soutraction = new Soustraction(expressionGauche, expressionDroite, 0);
        calcul.append(soutraction.toString());
        return calcul.toString();
    }

    /**
     * Cette comparaison est faite avec sgt :
     * sgti A, B, x :   A = 1 si B > x
     *                  A = 0 sinon
     *
     * On stocke x dans $t8.
     *
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *              A utiliser pour utiliser le beq ensuite.
     */
    String toStringComparaisonExpEtCste(Expression exp, ConstanteEntiere cste){
        System.out.println("on passe par toStringComparaisonExpEtCste de Superieur");
        StringBuilder calcul = new StringBuilder();
        calcul.append("# Début de comparaison entre une expression et une constante. \n");

        // On met la valeur de exp dans $v0...
        calcul.append(exp.toMIPS());

        // ...et la valeur de la cste dans $t8...

        // (cste.toString() écrit juste la valeur de la cste)
        calcul.append("\t li $t8, ");
        calcul.append(cste.toString());
        calcul.append("\n");

        // ... pour les utiliser dans sgt.
        calcul.append("\t sgt $v0, $v0, $t8");


        calcul.append("\n\n");
        return calcul.toString();
    }

    /**
     * Cette comparaison est faite avec sgt :
     * sgt A, B, C :    A = 1 si B > C
     *                  A = 0 sinon
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *               A utiliser pour utiliser le beq ensuite.
     */
    String toStringComparaisonExpEtExp(){
        StringBuilder calcul = new StringBuilder();

        return calcul.toString();

    }


    public String toString(){
        StringBuilder code = new StringBuilder();
        String typeExpGauche = expressionGauche.getType();
        String typeExpDroite = expressionDroite.getType();
        System.out.println("L'expression de gauche est : "+expressionGauche.toString() +" et de type : " + expressionGauche.getType());
        System.out.println("L'expression de droite est : "+expressionDroite.toString() +" et de type : " + expressionDroite.getType());

        boolean gaucheEstVariableOuCalcul = typeExpGauche.equals("Variable") || typeExpGauche.equals("Calcul") ;
        boolean droiteEstVariableOuCalcul = typeExpDroite.equals("Variable") || typeExpDroite.equals("Calcul");

        /* Cas où 1 > 2 */
        if (!gaucheEstVariableOuCalcul && !droiteEstVariableOuCalcul){
            code.append(toStringComparaisonCstEtCst());
        }

        /* Cas où x > 2 */
        if (gaucheEstVariableOuCalcul && !droiteEstVariableOuCalcul){
            code.append(toStringComparaisonExpEtCste(expressionGauche, (ConstanteEntiere)expressionDroite));
        }

        /* Cas où 2 > x */
        if (!gaucheEstVariableOuCalcul && droiteEstVariableOuCalcul){
            code.append(toStringComparaisonExpEtCste(expressionDroite, (ConstanteEntiere)expressionGauche));
        }

        if (gaucheEstVariableOuCalcul && droiteEstVariableOuCalcul){
            code.append(toStringComparaisonExpEtExp());
        }


        return code.toString();
    }


    public String getOperateur() {
        return "<";
    }
}
