package yal.exceptions;

public class SemantiqueException extends AnalyseException {

    public SemantiqueException() {
        super(MessagesErreursSemantiques.getInstance().toString());
    }
}
