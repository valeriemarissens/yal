package yal.tableSymboles;

public abstract class Symbole {
    private int deplacement;

    public Symbole(){
        deplacement = -1;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }
}
