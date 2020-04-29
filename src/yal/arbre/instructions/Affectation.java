package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.IndexTableau;
import yal.arbre.expressions.Variable;
import yal.exceptions.MessagesErreursSemantiques;

public class Affectation extends Instruction {
    private Variable partieGauche;
    private Expression partieDroite;
    private String registre;
    private Expression index;

    public Affectation(String id, Expression e, int n) {
        super(n);
        partieGauche = new Variable(id,n);
        partieDroite = e;
        registre = "($s7)";
    }

    /**
     * Constructeur si la partie gauche est un tableau.
     * @param idf
     * @param e
     * @param noLigne
     * @param index
     */
    public Affectation(String idf, Expression e, int noLigne, Expression index){
        super(noLigne);
        partieGauche = new IndexTableau(idf, noLigne, index);
        partieDroite = e;
        registre = "($s7)";
    }

    /**
     * Vérifie chacune des parties.
     * Dans le futur : vérifier que les types correspondent.
     */
    @Override
    public void verifier() { //TDS.identifier

        partieGauche.verifier();
        partieDroite.verifier();

        if (partieGauche.estVariableLocale()){
            registre = "($s2)";
        }

        /* Une affectation d'index de tableau ne peut contenir que des entiers. */
        if (partieGauche.getType().equals("IndexTableau")){
            if (partieDroite.getType().equals("CalculBooleen")){
                MessagesErreursSemantiques.getInstance().ajouter(noLigne,
                        "Un tableau ne peut contenir que des entiers. ");
            }
        }

    }

    public String toMIPS(){
        if (partieGauche.getType().equals("IndexTableau")){
           return toMIPSTableau();
        }else {
            return toMIPSVariable();

        }
    }

    /**
     * Les déplacements de variables sont dans l'arbre,
     * la TDS ne sert plus.
     *
     * @return le code équivalent à l'affectation
     * partieGauche = partieDroite en MIPS.
     */
    public String toMIPSVariable() {
        int deplacementGauche = partieGauche.getDeplacement();

        StringBuilder mips = new StringBuilder();
        mips.append("# Affectation "+partieGauche.toString()+" = "+partieDroite.toString()+" \n");
        mips.append(partieDroite.toMIPS());
        mips.append("\t sw $v0, "+deplacementGauche+registre+" \n");

        return mips.toString();
    }

    public String toMIPSTableau(){
        IndexTableau partieGauche = ((IndexTableau) this.partieGauche);
     StringBuilder mips = new StringBuilder();
     mips.append("\t #On met l'adresse de l'index demandé du tableau dans $t3. \n");
     mips.append(partieGauche.toMIPSIndex());

     mips.append("\t # On met la valeur de la partie droite dans $v0.\n");
     mips.append(partieDroite.toMIPS());

     mips.append("\t sw $v0, 0($t3) \n");
     return mips.toString();
    }

    @Override
    public String getType() {
        return "Affectation";
    }

}
