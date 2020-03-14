package yal.outils;

public class FabriqueIdentifiants {
    private int compteurCdt;
    private int compteurBoucle;
    private int compteurBloc;
    private int compteurVariableLocale;
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

    public int getCompteurVariableLocale(){
        compteurVariableLocale -= 4;
        return compteurVariableLocale;
    }

    /**
     * Reset au début de chaque nouvelle déclaration d'un ensemble de variables locales (voir la classe EnsembleVariablesLocales)
     */
    public void resetCompteurVariableLocale(){
        compteurVariableLocale = 4;
    }

    private FabriqueIdentifiants() {
        compteurCdt = 0;
        compteurBoucle = 0;
        compteurBloc = 0;
        // On commence les variables au 0 de la pile
        compteurVariableLocale = 4;
    }
}
