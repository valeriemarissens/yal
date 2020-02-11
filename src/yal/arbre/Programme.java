package yal.arbre;

import yal.exceptions.MessagesErreursSemantiques;
import yal.exceptions.SemantiqueException;
import yal.tableSymboles.TDS;

import java.util.ArrayList;

public class Programme extends ArbreAbstrait {
    private ArrayList<ArbreAbstrait> instructions;

    public Programme(int n){
        super(n);
        instructions = new ArrayList<>();

        System.out.println((1>0==2>1)!=3>4==5>5);
    }

    public void ajouter(ArbreAbstrait nouvelleInstruction){
        instructions.add(nouvelleInstruction);
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
        mips.append(".data\n");
        mips.append("retourLigne:  .asciiz \"\\n\"");
        mips.append("\n");
        mips.append("vrai: .asciiz \"vrai\"");
        mips.append("\n");
        mips.append("faux: .asciiz \"faux\"");
        mips.append("\n\n");
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

        mips.append("\n");
        mips.append("# Fin du programme : retour au systeme\n");
        mips.append("end :\n");
        mips.append("\t li $v0, 10\n");
        mips.append("\t syscall\n");

        return mips.toString();
    }
}
