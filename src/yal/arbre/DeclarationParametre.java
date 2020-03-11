package yal.arbre;

public class DeclarationParametre {

    private String idf;
    private int noLigne;

    public DeclarationParametre(String idf, int noLigne){
        this.idf = idf;
        this.noLigne = noLigne;
    }

    public String getIdf(){
        return idf;
    }

    public int getNoLigne(){
        return noLigne;
    }

    public void verifier(){}
}
