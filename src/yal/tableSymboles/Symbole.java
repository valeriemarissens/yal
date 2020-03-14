package yal.tableSymboles;

public abstract class Symbole {
    private int deplacement;
    private int numeroBloc ;

    public Symbole(){
        deplacement = -1;
        numeroBloc = 0;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setNumeroBloc(int numeroBloc){
        this.numeroBloc = numeroBloc;
    }

    public int getNumeroBloc(){
        return numeroBloc;
    }
}
