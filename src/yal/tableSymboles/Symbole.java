package yal.tableSymboles;

public abstract class Symbole {
    private int deplacement;
    private int nbBloc;

    public Symbole(){
        deplacement = -1;
        nbBloc = -1;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public void setNbBloc(int nbBloc) {
        this.nbBloc = nbBloc;
    }

    public int getNbBloc() {
        return nbBloc;
    }

    public int getDeplacement() {
        return deplacement;
    }
}
