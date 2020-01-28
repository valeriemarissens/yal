package yal.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder("\t # On range "+cste+" dans $v0\n");
        mips.append("\t li $v0, "+cste+"\n");
        return mips.toString();
    }

}
