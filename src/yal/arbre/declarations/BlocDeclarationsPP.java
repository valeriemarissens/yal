package yal.arbre.declarations;

import java.util.ArrayList;

/**
 * Lorsqu'on déclare une variable (constante, tableau ou fonction) dans le PP,
 * on instancie cette classe.
 *
 */
public class BlocDeclarationsPP extends BlocDeclarations {
    private ArrayList<DeclarationFonction> fonctions;

    public BlocDeclarationsPP(int n) {
        super(n);

        // TODO : voir si ceci est important.
        setNumeroBloc(0);

        fonctions = new ArrayList<>();
    }

    /**
     * Depuis Grammaire.cup, on ajoute une fonction dans les déclarations.
     * @param fonction
     */
    public void ajouter(DeclarationFonction fonction) {
        fonctions.add(fonction) ;
    }

    /**
     * Puisqu'on est dans le PP, on doit vérifier les initialisations :
     *              - des fonctions,
     *              - des tableaux,
     *              - des variables (rien à vérifier).
     */
    @Override
    public void verifier() {
        for (DeclarationFonction f : fonctions){
            f.verifier();
        }
        for (DeclarationTableau tableau : tableaux){
            tableau.verifier();
        }
    }

    /**
     * On divise en deux parties le MIPS car le texte MIPS doit afficher les fonctions en bas du code mais les déclarations
     * de tableaux en haut du code.
     * Donc, cette fonction n'est pas utilisée, ce sont tableauxToMIPS() et fonctionsToMIPS() qui le sont.
     * @return
     */
    @Override
    public String toMIPS() {
        return "";
    }

    /**
     * Code des instructions des fonctions à la fin du programme.
     * @return
     */
    public String fonctionsToMIPS() {
        StringBuilder mips = new StringBuilder();

        if (fonctions.size() != 0) {
            mips.append("# Déclaration des fonctions \n");
            for (DeclarationFonction fonction : fonctions) {
                mips.append(fonction.toMIPS());
            }
        }

        return mips.toString();
    }

    public String libererTableauxToMIPS(){
        return "";
    }

    @Override
    public String getType() {
        return "BlocDeclarationsPP";
    }



}
