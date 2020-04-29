package yal.tableSymboles;

public class SymboleVariable extends Symbole{
    private int deplacement;

    private boolean estTableau;

    public SymboleVariable(){
        deplacement = -1;
    }


    public boolean estTableau(){ return estTableau; }

    public void setEstTableau(boolean estTableau){ this.estTableau = estTableau;}

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

}
