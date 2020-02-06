package yal.arbre.instructions;

import yal.arbre.ArbreAbstrait;
import yal.arbre.expressions.Expression;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdCondition;

public class Condition extends Instruction {
    private int identifiant;
    String nomEtiquetteSi ;
    String nomEtiquetteSinon;
    String nomEtiquetteFinsi;
    ArbreAbstrait blocSi;
    ArbreAbstrait blocSinon;
    Expression expression;
    private boolean estInutile = false;


    /**
     * Constructeur pour - si alors () fsi
     *                   - si alors () sinon fsi
     *                   - si alors () sinon () fsi
     *
     * Un seul constructeur pour pouvoir différencier la liste d'instructions venant du si
     * et la liste d'instruction venant du sinon dans les 3 cas.
     * Puisque cette condition est utile, le constructeur demande un numéro à la fabrique.
     *
     * @param exp
     * @param bsi
     * @param bsinon
     * @param n
     */
    public Condition(Expression exp, ArbreAbstrait bsi, ArbreAbstrait bsinon, int n){
        super(n);
        expression = exp;
        blocSi = bsi;
        blocSinon = bsinon;
        identifiant = FabriqueIdCondition.getInstance().getNumero();
        nomEtiquetteSi = "si"+identifiant;
        nomEtiquetteSinon = "sinon"+identifiant;
        nomEtiquetteFinsi = "finsi"+identifiant;
    }

    /**
     * Constructeur si syntaxe : si alors fsi (donc inutile de générer du code mips)
     * (et oui c'est syntaxiquement correct)
     *
     * Demande par contre un if supplémentaire dans verifier().
     * @param n
     */
    public Condition(int n) {
        super(n);
        estInutile = true;
    }


    @Override
    public void verifier() {
        if (!estInutile) {
            if (!expression.getType().equals("Comparaison")) {
                int ligneErreur = expression.getNoLigne();
                MessagesErreursSemantiques.getInstance().ajouter(ligneErreur,"condition mal exprimée (penser aux parenthèses si besoin).");
            }
            if (blocSi != null) {
                blocSi.verifier();
            }
            if (blocSinon != null) {
                blocSinon.verifier();
            }
        }
    }

    private String toMipsBloc(ArbreAbstrait arbre){
        if (arbre==null){
            return "";
        }else{
            return arbre.toMIPS();
        }
    }


    /**
     * Modèle de traduction pris :
     * (cf https://www.cs.fsu.edu/~hawkes/cda3101lects/chap3/ifthenelse.html)
     *      slt $v0, $v0, $t8
     *      beq $v0, $0, nomEtiquetteSinon
     *      instructions de si
     *      j endif
     *
     *  nomEtiquetteSinon :
     *      instructions de sinon
     *
     *  nomEtiquetteFinsi :
     *      # Rien par ici, ça continue juste son chemin dans la vie apparemment
     *
     *
     * @return
     */
    @Override
    public String toMIPS() {
        StringBuilder code = new StringBuilder();

        if (!estInutile){
            code.append(expression.toString());
            StringBuilder branchement = new StringBuilder();

            branchement.append("\t # Évaluation comparaison et branchement \n");

            // Si $v0 = 0, alors la comparaison est fausse : étiquette sinon
            // beq $v0, $0, sinon
            branchement.append("\t beq $v0, $0, ");
            branchement.append(nomEtiquetteSinon);
            branchement.append("\n\n");

            // instructions du bloc si
            branchement.append(toMipsBloc(blocSi));

            // j endif
            branchement.append("\t j ");
            branchement.append(nomEtiquetteFinsi);
            branchement.append("\n\n");

            // sinon :
            //      instructions du bloc sinon
            branchement.append(nomEtiquetteSinon);
            branchement.append(": \n");
            branchement.append(toMipsBloc(blocSinon));
            branchement.append("\n");

            // finsi :
            //      # vit sa vie
            branchement.append(nomEtiquetteFinsi);
            branchement.append(": \n");
            branchement.append("\t # On continue...\n");
            code.append(branchement);
        }

        return code.toString();
    }
}
