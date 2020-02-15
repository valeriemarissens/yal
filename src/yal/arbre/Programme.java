package yal.arbre;

import yal.exceptions.MessagesErreursSemantiques;
import yal.exceptions.SemantiqueException;
import yal.tableSymboles.TDS;

import java.util.ArrayList;
import java.util.HashMap;

public class Programme extends ArbreAbstrait {
    private ArrayList<ArbreAbstrait> instructions;
    private HashMap<String, ArbreAbstrait> instructionsFonctions;

    public Programme(int n){
        super(n);
        instructions = new ArrayList<>();
        instructionsFonctions = new HashMap<>();
    }

    public void ajouter(ArbreAbstrait nouvelleInstruction){
        instructions.add(nouvelleInstruction);
    }

    // où l'instancier ?
    public void ajouterFonction(String idFonction, ArbreAbstrait nouvelleInstructionFonction){
        instructionsFonctions.put(idFonction, nouvelleInstructionFonction);
    }

    @Override
    public void verifier() throws SemantiqueException {
        for (ArbreAbstrait instruction : instructions) {
            instruction.verifier();
        }
        MessagesErreursSemantiques messages = MessagesErreursSemantiques.getInstance();
        if (!messages.isEmpty()){
            throw new SemantiqueException();
        }
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        // macros :
        mips.append(".data\n");
        mips.append("retourLigne:  .asciiz \"\\n\"");
        mips.append("\n");
        mips.append("vrai: .asciiz \"vrai\"");
        mips.append("\n");
        mips.append("faux: .asciiz \"faux\"");
        mips.append("\n");
        mips.append("divisionZero : .asciiz \"ERREUR EXECUTION : Vous avez essayé de diviser par zéro.\"");
        mips.append("\n\n");

        // programme :
        mips.append(".text\n");
        mips.append("# Debut du programme mips\n");
        mips.append("main :\n");

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

        for (ArbreAbstrait instruction : instructions) {
            mips.append(instruction.toMIPS());
        }

        // fin :
        mips.append("\n");
        mips.append("# Fin du programme : retour au systeme\n");
        mips.append("end :\n");
        mips.append("\t li $v0, 10\n");
        mips.append("\t syscall\n");

        mips.append(codeFonctionsToMIPS());

        return mips.toString();
    }

    private String codeFonctionsToMIPS(){
        StringBuilder mips = new StringBuilder();

        mips.append("\n\n");
        mips.append("# Fonctions : ");

        instructionsFonctions.forEach( (idFonction, instruction ) -> {
                mips.append("fonction" + idFonction + " :");
                mips.append(instruction.toMIPS());
            }
        );

        return mips.toString();
    }
}
