package yal.tableSymboles;

public class SymboleFonction extends Symbole {
    private int numBloc;
    private String idf;
    private int tailleZoneVars;

    public SymboleFonction(String nom){
        idf = nom;
    }

    public void setNumBloc(int n){
        numBloc=n;
    }

    public String getNomEtiquette(){
        return "fonction_"+idf;
    }
}
