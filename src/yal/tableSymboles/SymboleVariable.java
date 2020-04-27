package yal.tableSymboles;

public class SymboleVariable extends Symbole{
    private int deplacement;

    public SymboleVariable(){
        deplacement = -1;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

}
