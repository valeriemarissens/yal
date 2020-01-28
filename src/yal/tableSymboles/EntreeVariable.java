package yal.tableSymboles;

import java.util.Objects;

public class EntreeVariable extends Entree {
    private String idf;

    public EntreeVariable(String v, int n){
        super(n);
        idf = v;
    }

    public String getIdf() {
        return idf;
    }

    public void setIdf(String variable) {
        this.idf = variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntreeVariable that = (EntreeVariable) o;
        return Objects.equals(idf, that.idf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idf);
    }
}
