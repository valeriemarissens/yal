package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.Soustraction;

public class Comparaison extends Expression {
    Expression expressionGauche;
    Expression expressionDroite;
    String cmdMips;

    protected Comparaison(int n) {
        super(n);
    }

    public Comparaison(Expression e1, Expression e2, String op, int i){
        super(i);
        expressionGauche = e1;
        expressionDroite = e2;

        if (op.equals(">")){
            cmdMips = "sgt";
        }else if (op.equals("<")){
            cmdMips = "slt";
        }

    }

    public String getOperateur(){
        if (cmdMips.equals("sgt")){
            return ">";
        }else if (cmdMips.equals("slt")){
            return "<";
        }
        return "";
    }

    /**
     * On va stocker la valeur de l'exp de gauche dans $v0 et celle de l'exp de droite dans $t4.
     *
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *              A utiliser pour utiliser le beq ensuite.
     */
    private String toStringComparaisonCstEtCst(){
        StringBuilder calcul = new StringBuilder();
        calcul.append("# Début de comparaison entre deux entiers. \n ");
        calcul.append("\t li $v0, ");

        // Le toMIPS d'une constante n'est que sa valeur
        calcul.append(expressionGauche.toString());
        calcul.append("\n");

        calcul.append("\t li $t4, ");
        calcul.append(expressionDroite.toString());
        calcul.append("\n");

        calcul.append("\t ");
        calcul.append(cmdMips);
        calcul.append(" $v0, $v0, $t4");

        calcul.append("\n\n");

        return calcul.toString();
    }

    private String initialisationComparaisonCsteExp(Expression exp, ConstanteEntiere cste){
        StringBuilder calcul = new StringBuilder();

        // On initialise $v0 et $t4...
        calcul.append(initToStringComparaisonExpCste(exp, cste));

        // ... pour les utiliser dans sgt/slt.
        calcul.append("\t ");
        calcul.append(cmdMips);
        return calcul.toString();
    }

    /**
     * Cette comparaison est faite avec sgt/slt.
     *
     * On stocke x dans $t4.
     * On stocke l'expression dans $v0.
     *
     * La différence entre toStringComparaisonExpEtCste et
     * toStringComparaisonExpEtCste est l'ordre des variables dans sgt/slt.
     *
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *              A utiliser pour utiliser le beq ensuite.
     */
    private String toStringComparaisonExpEtCste(Expression exp, ConstanteEntiere cste){
        StringBuilder calcul = new StringBuilder();
        calcul.append(initialisationComparaisonCsteExp(exp, cste));
        calcul.append(" $v0, $v0, $t4");

        calcul.append("\n");
        return calcul.toString();
    }

    /**
     * Cette comparaison est faite avec sgt/slt :
     *
     * On stocke x dans $t4.
     * On stocke l'expression dans $v0.
     *
     * La différence entre toStringComparaisonExpEtCste et
     * toStringComparaisonExpEtCste est l'ordre des variables dans sgt/slt.
     *
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *              A utiliser pour utiliser le beq ensuite.
     */
    private String toStringComparaisonCsteEtExp(Expression exp, ConstanteEntiere cste){
        StringBuilder calcul = new StringBuilder();
        calcul.append(initialisationComparaisonCsteExp(exp, cste));
        calcul.append(" $v0, $t4, $v0");
        calcul.append("\n");
        return calcul.toString();
    }

    private String initToStringComparaisonExpCste(Expression exp, ConstanteEntiere cste){
        StringBuilder init = new StringBuilder();
        init.append("# Début de comparaison entre une expression et une constante. \n");

        // On met la valeur de exp dans $v0...
        init.append(exp.toMIPS());

        // ...et la valeur de la cste dans $t4...

        // (cste.toMIPS() écrit juste la valeur de la cste)
        init.append("\t li $t4, ");
        init.append(cste.toString());
        init.append("\n");
        return init.toString();
    }

    /**
     * Cette comparaison est faite avec sgt/slt.
     *
     * On stocke l'exp de gauche dans $v0.
     * On stocke l'exp de droite dans $t4 :
     *          il faut déjà commencer par calculer l'exp de droite pour pouvoir la mettre dans $t4.
     *
     * @return le code pour calculer la comparaison et stocker le res dans $v0.
     *               A utiliser pour utiliser le beq ensuite.
     */
    private String toStringComparaisonExpEtExp(){
        StringBuilder calcul = new StringBuilder();
        calcul.append("# Début de comparaison entre deux expressions. \n");

        // Le résultat de expressionDroite est gardé dans $v0.
        calcul.append(expressionDroite.toMIPS());

        // Mais on aura besoin de $v0 pour expressionGauche, alors on le met dans $t4.
        calcul.append("\t move $t4, $v0 \n");

        // Maintenant, on garde le résultat de expressionGauche dans $v0.
        calcul.append(expressionGauche.toMIPS());

        // Et on peut faire la comparaison.
        calcul.append("\t");
        calcul.append(cmdMips);
        calcul.append(" $v0, $v0, $t4");
        calcul.append("\n\n");

        return calcul.toString();

    }

    @Override
    public String toMIPS(){
        StringBuilder code = new StringBuilder();
        String typeExpGauche = expressionGauche.getType();
        String typeExpDroite = expressionDroite.getType();
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
            code.append(toStringComparaisonCsteEtExp(expressionDroite, (ConstanteEntiere)expressionGauche));
        }

        /* Cas où a > b */
        if (gaucheEstVariableOuCalcul && droiteEstVariableOuCalcul){
            code.append(toStringComparaisonExpEtExp());
        }


        return code.toString();
    }

    @Override
    public void verifier() {
        expressionGauche.verifier();
        expressionDroite.verifier();
    }

    @Override
    public String toString(){
        return expressionGauche.toString()+getOperateur()+expressionDroite.toString();
    }

    public String getType(){
        return "Comparaison";
    }
}
