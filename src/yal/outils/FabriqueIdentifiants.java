package yal.outils;

public class FabriqueIdentifiants {
    private int compteurCdt;
    private int compteurBoucle;
    private int compteurBloc;
    private static FabriqueIdentifiants ourInstance = new FabriqueIdentifiants();

    public static FabriqueIdentifiants getInstance() {
        return ourInstance;
    }

    public int getNumeroCondition(){
        compteurCdt++;
        return compteurCdt;
    }

    public int getNumeroBoucle(){
        compteurBoucle++;
        return compteurBoucle;
    }

    public int getNumeroBloc(){
        compteurBloc++;
        return compteurBloc;
    }

    private FabriqueIdentifiants() {
        compteurCdt = 0;
        compteurBoucle = 0;
        compteurBloc = 0;
    }
}
