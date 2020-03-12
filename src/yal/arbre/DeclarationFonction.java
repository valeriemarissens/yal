package yal.arbre;


import yal.arbre.expressions.Variable;
import yal.arbre.instructions.Retourne;
import yal.exceptions.MessagesErreursSemantiques;
import yal.outils.FabriqueIdentifiants;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.EntreeVariableLocale;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

public class DeclarationFonction extends ArbreAbstrait {
    String nom;
    BlocDInstructions instructions;
    SymboleFonction symbole;
    EntreeFonction entree;
    EnsembleParametres parametres;
    EnsembleVariablesLocales variablesLocales;

    public DeclarationFonction(String nomFonc, EnsembleParametres parametres,  EnsembleVariablesLocales variablesLocales, BlocDInstructions blocy, int n) {
        super(n);
        nom = nomFonc;
        instructions = blocy;
        this.parametres = parametres;
        this.variablesLocales = variablesLocales;

        if (parametres==null){
            entree = new EntreeFonction(nom, n, 0) ;
            symbole = new SymboleFonction(nom, 0);
        }else{
            int nbParametres = parametres.getNbParametres();
            entree = new EntreeFonction(nom, n, nbParametres) ;
            symbole = new SymboleFonction(nom, nbParametres);
        }

        TDS.getInstance().ajouter(entree, symbole) ;
        symbole.setNumBloc(FabriqueIdentifiants.getInstance().getNumeroBloc());
    }


    @Override
    public void verifier() {
        if (parametres != null) {
            parametres.verifier();

        }

        if (variablesLocales!=null){
            variablesLocales.verifier();
        }

        instructions.verifier();
        if (!contientRetourne()){
            String messageExplicite = "La fonction doit retourner un entier.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        }

    }

    @Override
    /**
     * On y génère l'étiquette et le code MIPS des instructions à l'intérieur de la fonction.
     * Le nom de l'étiquette est dans le symbole (nécessaire pour AppelFonction).     *
     */
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();

        // Génère l'étiquette
        mips.append(symbole.getNomEtiquette());
        mips.append(" : \n");

        // Code pour empiler toutes les informations du bloc
        mips.append(toMIPSEntree());

        mips.append(instructions.toMIPS());

        // Code pour sortir de la fonction
        // C'est en fait la classe Retourne qui s'en occupe
        return mips.toString();
    }


    private String toMIPSEntree(){
        StringBuilder mips = new StringBuilder();

        // Paramètres empilés précédemment par AppelFonction avant le branchement.

        // Adresse de retour empilée.
        mips.append("\t # On empile l'adresse de retour pour retourner à l'endroit de l'appel. \n");
        mips.append("\t move $v0, $ra \n");
        mips.append(toMIPSEmpiler());
        mips.append("\n");

        // Chaînage dynamique empilé.
        // L'ancienne base de variables locales du bloc antérieur était dans $s2.
        mips.append("\t # On empile le chaînage dynamique. \n");
        mips.append("\t move $v0, $s2 \n");
        mips.append(toMIPSEmpiler());
        mips.append("\n");

        // Numéro de bloc empilé.
        mips.append("\t # On empile le numéro de bloc. \n");
        mips.append("\t li $v0, "+symbole.getNumBloc()+" \n");
        mips.append(toMIPSEmpiler());

        // Variables locales empilées.
        if (variablesLocales != null) {
            mips.append(toMIPSVariablesLocales());
        }

        return mips.toString();
    }

    private String toMIPSVariablesLocales(){
        StringBuilder mips = new StringBuilder();

        mips.append("\t # On empile les variables \n");
        for (DeclarationVariableLocale variableLocale : variablesLocales) {
            mips.append(variableLocale.toMIPS());
            mips.append(toMIPSEmpiler());
            mips.append("\n");
        }

        return mips.toString();
    }

    /**
     * La valeur à empiler doit être dans $v0.
     * @return empiler en MIPS.
     */
    private String toMIPSEmpiler(){
        StringBuilder mips = new StringBuilder();

        mips.append("\t sw $v0, 0($sp) \n");
        mips.append("\t add $sp, $sp, -4 \n");

        return mips.toString();
    }

    @Override
    public String getType() {
        return "DeclarationFonction";
    }

    @Override
    public boolean contientRetourne() {
        return instructions.contientRetourne();
    }


    /**
     * Pour vérifier qu'il y a bien les instructions écrites dans la fonction
     */
    public void toSoutDebug(){
        System.out.println("Ces sout EXCLUSIFS vous sont proposés par la classe Fonction de yal.arbre !");
        System.out.println(nom);
        System.out.println(instructions.toMIPS());
    }

}
