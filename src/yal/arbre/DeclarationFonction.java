package yal.arbre;


import yal.exceptions.MessagesErreursSemantiques;
import yal.tableSymboles.EntreeFonction;
import yal.tableSymboles.Symbole;
import yal.tableSymboles.SymboleFonction;
import yal.tableSymboles.TDS;

public class DeclarationFonction extends ArbreAbstrait {
    String nom;
    BlocDInstructions instructions;
    SymboleFonction symbole;

    public DeclarationFonction(String nomFonc, BlocDInstructions blocy, int n) {
        super(n);
        nom = nomFonc;
        instructions = blocy;

        EntreeFonction f = new EntreeFonction(nom, n) ;
        symbole = new SymboleFonction(nom) ;
        TDS.getInstance().ajouter(f, symbole) ;

    }

    @Override
    public void verifier() {
        instructions.verifier();
        if (!instructions.existsRetourne()){
            String messageExplicite = "La fonction doit retourner un entier.";
            MessagesErreursSemantiques.getInstance().ajouter(noLigne, messageExplicite);
        };
    }

    @Override
    /**
     * On y génère l'étiquette et le code MIPS des instructions à l'intérieur de la fonction.
     * Le nom de l'étiquette est dans le symbole (nécessaire pour AppelFonction).
     *
     * TODO Dès qu'il y a l'instruction 'retourne', il faut arrêter de parcourir les instructions et faire le jr (jump).
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

        mips.append("# On empile l'adresse de retour pour retourner à l'endroit de l'appel. \n");
        mips.append("\t add $sp, $sp, -4 \n");
        mips.append("\t sw $ra, 0($sp) \n");

        return mips.toString();
    }



    @Override
    public String getType() {
        return "DeclarationFonction";
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
