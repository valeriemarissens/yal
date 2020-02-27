package yal.arbre;

import yal.exceptions.MessagesErreursSemantiques;
import yal.exceptions.SemantiqueException;
import yal.tableSymboles.TDS;

import java.util.ArrayList;
import java.util.HashMap;

public class Programme extends ArbreAbstrait {
    private ArrayList<ArbreAbstrait> instructions;
    private ArrayList<DeclarationFonction> fonctions;

    public Programme(int n){
        super(n);
        instructions = new ArrayList<>();
        fonctions = new ArrayList<>();
    }

    public void ajouter(ArbreAbstrait nouvelleInstruction){
        instructions.add(nouvelleInstruction);
    }

    public void setFonctions(BlocDeclFonctions bloc){
        for (DeclarationFonction fonction : bloc){
            fonctions.add(fonction);
        }
    }

    @Override
    public void verifier() throws SemantiqueException {
        for (ArbreAbstrait instruction : instructions) {
            instruction.verifier();
        }
        for (DeclarationFonction fonction : fonctions){
            fonction.verifier();
        }

        MessagesErreursSemantiques messages = MessagesErreursSemantiques.getInstance();
        if (!messages.isEmpty()){
            throw new SemantiqueException();
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        // Macros
        mips.append(".data\n");
        mips.append("retourLigne:  .asciiz \"\\n\"");
        mips.append("\n");
        mips.append("vrai: .asciiz \"vrai\"");
        mips.append("\n");
        mips.append("faux: .asciiz \"faux\"");
        mips.append("\n");
        mips.append("divisionZero : .asciiz \"ERREUR EXECUTION : Vous avez essayé de diviser par zéro.\"");
        mips.append("\n\n");

        // Programme général
        mips.append(".text\n");
        mips.append("# Debut du programme mips\n");
        mips.append("main :\n");

        // Initialisation des piles
        int tailleZoneVariables = TDS.getInstance().getTailleZoneVariable();
        if (tailleZoneVariables != 0) {
            int nbVariables = -tailleZoneVariables/4;

            mips.append("\t # Initialiser s7 avec sp (base des variables)\n");
            mips.append("\t move $s7,$sp \n");
            mips.append("\n");
            mips.append("\t # Réservation de l'espace pour "+nbVariables+" variable(s)\n");
            mips.append("\t addi $sp, $sp, "+tailleZoneVariables);
            mips.append("\n\n");
        }

        // Instructions du programme
        for (ArbreAbstrait instruction : instructions) {
            mips.append(instruction.toMIPS());
        }

        // Retour système pour fin programme
        mips.append("\n");
        mips.append("# Fin du programme : retour au systeme\n");
        mips.append("end :\n");
        mips.append("\t li $v0, 10\n");
        mips.append("\t syscall\n");

        // Ajout des fonctions
        mips.append(codeFonctionsToMIPS());

        return mips.toString();
    }

    @Override
    public String getType() {
        return "Programme";
    }

    private String codeFonctionsToMIPS(){
        StringBuilder mips = new StringBuilder();

        mips.append("\n\n");
        mips.append("# Fonctions : \n ");

        for (DeclarationFonction fonction : fonctions){
            mips.append(fonction.toMIPS());
            mips.append("\n");
        }

        return mips.toString();
    }
}
