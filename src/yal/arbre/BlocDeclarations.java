package yal.arbre;

import java.util.ArrayList;

public class BlocDeclarations extends ArbreAbstrait{
    ArrayList<DeclarationFonction> fonctions;
    ArrayList<DeclarationTableau> tableaux;

    public BlocDeclarations(int n) {
        super(n);
        fonctions = new ArrayList<>();
        tableaux = new ArrayList<>();

        // TODO : enlever sout
        System.out.println("Un bloc de déclarations a été créé.");
    }

    public void ajouter(DeclarationFonction fonction) {
        fonctions.add(fonction) ;
    }

    public void ajouter(DeclarationTableau tableau) {
        // TODO : enlever sout
        System.out.println("Un tableau a été déclaré.");
        tableaux.add(tableau);
    }

    @Override
    public void verifier() {
        for (DeclarationFonction f : fonctions){
            f.verifier();
        }
        for (DeclarationTableau tableau : tableaux){
            tableau.verifier();
        }
    }

    @Override
    public String toMIPS() {
        return "";
    }

    /**
     * Code d'initialisation des tableaux au début du programme.
     * @return
     */
    public String tableauxToMIPS(){
        StringBuilder mips = new StringBuilder();

        mips.append("# Déclaration, initialisation des tableaux \n");
        for (DeclarationTableau tableau : tableaux){
            mips.append(tableau.toMIPS());
        }

        return mips.toString();
    }

    /**
     * Code des instructions des fonctions à la fin du programme.
     * @return
     */
    public String fonctionsToMIPS() {
        StringBuilder mips = new StringBuilder();


        mips.append("# Déclaration des fonctions \n");
        for (DeclarationFonction fonction : fonctions){
            mips.append(fonction.toMIPS());
        }

        return mips.toString();
    }

    @Override
    public String getType() {
        return "BlocDeclarations";
    }

    @Override
    public boolean contientRetourne() {
        for (DeclarationFonction f : fonctions){
            if (f.contientRetourne()){
                return true;
            }
        }
        return false;
    }



}
