package yal.arbre.expressions;

public class ConstanteEntiere extends Constante {
    
    public ConstanteEntiere(String texte, int n) {
        super(texte, n) ;
    }

    @Override
    public String toMIPS() {
        StringBuilder mips = new StringBuilder();
        mips.append("\t li $v0, "+cste);
        mips.append("\t\t # on range "+cste+" dans $v0 \n");
        return mips.toString();
    }

    @Override
    public String getType() {
        return "ConstanteEntiere";
    }
}
