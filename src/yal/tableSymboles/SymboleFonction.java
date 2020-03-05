package yal.tableSymboles;

public class SymboleFonction extends Symbole {
    private int numBloc;
    private String idf;
    private int tailleZoneVars;
    private String idParametre;

    public SymboleFonction(String nom){
        idf = nom;
    }

    public void setNumBloc(int n){
        numBloc=n;
    }

    public String getNomEtiquette(){
        return "fonction_"+idf;
    }

    public void setIdParametre(String idParametre) {
        this.idParametre = idParametre;
    }

    public String getIdParametre(){
        return this.idParametre;
    }

}
