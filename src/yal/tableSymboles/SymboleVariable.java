package yal.tableSymboles;

public class SymboleVariable extends Symbole{
    private int deplacement;
    private boolean estParametre;

    public SymboleVariable(){
        deplacement = -1;
        estParametre = false;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public void setEstParametre(boolean bool){
        estParametre = bool;
    }

    public boolean estParametre(){
        return estParametre;
    }
}
