package yal.outils;

public class FabriqueIdentifiants {
    private int compteurCdt;
    private int compteurBoucle;
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

    private FabriqueIdentifiants() {
        compteurCdt = 0;
        compteurBoucle = 0;
    }
}
