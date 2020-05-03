package yal.arbre.instructions;

import yal.arbre.expressions.Expression;
import yal.arbre.expressions.IndexTableau;
import yal.arbre.expressions.Variable;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;

public class Affectation extends Instruction {
    private Variable partieGauche;
    private Expression partieDroite;
    private String registre;

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

        /* Une affectation de tableau ne peut se faire qu'avec un autre tableau. */
        boolean test1 = partieGauche.getType().equals("Tableau") && !partieDroite.getType().equals("Tableau");
        boolean test2 = !partieGauche.getType().equals("Tableau") && partieDroite.getType().equals("Tableau");
        boolean test = test1  || test2 ;

        if (test){
            MessagesErreursSemantiques.getInstance().ajouter(noLigne,
                    "Une affectation d'un tableau ne peut que se faire qu'avec un autre tableau. ");
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
        }else if (partieGauche.getType().equals("Tableau") && partieDroite.getType().equals("Tableau")) {
            return toMIPSTableaux();
        }else {
            return toMIPSVariable();
        }
    }

    private String toMIPSTableaux(){
        Variable tableauGauche = (Variable) partieGauche;
        Variable tableauDroite = (Variable) partieDroite;

        int deplacementGauche = tableauGauche.getDeplacement();
        int deplacementDroite = tableauDroite.getDeplacement();

        String registreGauche = tableauGauche.getRegistre();
        String registreDroite = tableauDroite.getRegistre();

        int numero = FabriqueIdentifiants.getInstance().getNumeroCondition();
        String etiquetteBoucle = "boucle_affectationTableaux_"+numero;
        String etiquetteFin = "fin_"+etiquetteBoucle;

        StringBuilder mips = new StringBuilder();

        mips.append("\t # Si la taille des tableaux n'est pas égale, alors on arrête l'exécution. \n");
        mips.append("\t lw $v0, "+ deplacementGauche + registreGauche + "\n");
        mips.append("\t lw $t8, " + deplacementDroite + registreDroite + " \n");
        // $v0 = 0 si les nombres sont égaux
        mips.append("\t subu $v0, $v0, $t8 \n");
        // $v0 devient 1 si les nombres ne sont pas égaux (réduit à 1 au lieu de 2, 3, 4...).
        mips.append("\t sltu $v0, $0, $v0 \n");
        mips.append("\t bgtz $v0, erreur_taillesEgalite \n\n");

        /* À ce moment là, $t8 contient le nombre d'éléments des tableaux. */
        mips.append("\t # On charge dans $t3 l'@OV du tableau de gauche et dans $t7 l'@OV du tableau de droite. \n");
        mips.append("\t la $t3, " + (deplacementGauche-4) + registreGauche + "\n" );
        mips.append("\t la $t7, " + (deplacementDroite-4) + registreDroite + "\n");
        mips.append("\n");

        mips.append(etiquetteBoucle+ ": \n");
        /* Si $t8 est égal à 0, on quitte. */
        mips.append("\t blez $t8,"+ etiquetteFin+ "\n");
        mips.append("\n");

        /* On utilise $v0 == tableau de gauche, donc celui à qui on donne les valeurs. */
        mips.append("\t lw $v0, 4($t7) \n ");
        mips.append("\t sw $v0, 4($t3) \n");

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

        mips.append("\t "+ etiquetteFin + ": \n");
        return mips.toString();
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
