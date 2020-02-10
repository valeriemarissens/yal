package yal.arbre.expressions.comparaisons;

import yal.arbre.expressions.ConstanteEntiere;
import yal.arbre.expressions.Expression;
import yal.arbre.expressions.calculs.CalculBooleen;
import yal.arbre.expressions.calculs.Soustraction;
import yal.outils.FabriqueIdentifiants;

public class Comparaison extends CalculBooleen {
    private String cmdMips;


    public Comparaison(Expression e1, Expression e2, String op, int i){
        super(e1, e2, i);

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

    public String titreOperation(){
        return " comparaison entre deux expressions. \n";
    }

    public String calculOperation(){
        StringBuilder calcul = new StringBuilder();

        calcul.append("\t");
        calcul.append(cmdMips);
        calcul.append(" $v0, $t8, $v0");
        calcul.append("\n");

        return calcul.toString();

    }

}
