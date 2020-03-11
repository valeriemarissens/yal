package yal.arbre;


import yal.exceptions.MessagesErreursSemantiques;
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

        entree = new EntreeFonction(nom, n) ;

        if (parametres==null){
            symbole = new SymboleFonction(nom, 0);
        }else {
            symbole = new SymboleFonction(nom, parametres.getNbParametres());
        }

        TDS.getInstance().ajouter(entree, symbole) ;
        TDS.getInstance().entreeBloc(symbole.getNbBloc());
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

        // Code pour enregistrer l'adresse de retour
        mips.append(toMIPSEntree());

        mips.append(instructions.toMIPS());

        // Code pour sortir de la fonction
        // C'est en fait la classe Retourne qui s'en occupe
        return mips.toString();
    }


    private String toMIPSEntree(){
        StringBuilder mips = new StringBuilder();

        if (parametres != null) {
            mips.append("\t # On empile le(s) paramètre(s). \n");
            //parametres.toMIPS();
            mips.append("\t sw $v0, 0($sp) \n");
            mips.append("\t add $sp, $sp, -4 \n");
            mips.append("\n");
            // ici on a bien le paramètre dans $v0 et dans la pile
        }

        mips.append("\t # On empile l'adresse de retour pour retourner à l'endroit de l'appel. \n");
        mips.append("\t sw $ra, 0($sp) \n");
        mips.append("\t add $sp, $sp, -4 \n");
        mips.append("\n");

        // On empile numéro de région ?

        // mips.append("\t # On empile les variables \n");
        // TODO : variables to mips.

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
