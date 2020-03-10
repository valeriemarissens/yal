package yal.tableSymboles;

public class SymboleFonction extends Symbole {
    private int numBloc;
    private String idf;
    private int tailleZoneVars;
    private String idParametre;
    private int nombreParametres;

    public SymboleFonction(String nom, int nbParams){
        idf = nom;
        nombreParametres = nbParams;
    }

    public void setNumBloc(int n){
        numBloc=n;
    }

    public String getNomEtiquette(){
        return "fonction_"+idf+"_"+nombreParametres;
    }

    public void setIdParametre(String idParametre) {
        this.idParametre = idParametre;
    }

    public String getIdParametre(){
        return this.idParametre;
    }

}
