package yal.outils;

public class FabriqueIdCondition {
    private int compteur;
    private static FabriqueIdCondition ourInstance = new FabriqueIdCondition();

    public static FabriqueIdCondition getInstance() {
        return ourInstance;
    }

    public int getNumero(){
        compteur++;
        return compteur;
    }

    private FabriqueIdCondition() {
        compteur=0;
    }
}
