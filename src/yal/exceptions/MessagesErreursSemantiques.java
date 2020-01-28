package yal.exceptions;

import java.util.ArrayList;

public class MessagesErreursSemantiques {
    private static MessagesErreursSemantiques ourInstance = new MessagesErreursSemantiques();
    private ArrayList<String> messages;

    private MessagesErreursSemantiques() {
        messages = new ArrayList<>();
    }

    public void ajouter(int ligne, String messageExplicite){
        String nouveauMess = "ERREUR SEMANTIQUE : ligne " + ligne + "\n\t" + messageExplicite + "\n";
        messages.add(nouveauMess);
    }

    /**
     * Concatène tous les messages gardés.
     * @return tous les messages.
     */
    @Override
    public String toString(){
        StringBuilder ensembleMessages = new StringBuilder();
        for (String m : messages){
            ensembleMessages.append(m);
        }
        return ensembleMessages.toString();
    }

    public boolean isEmpty(){
        return messages.isEmpty();
    }

    public static MessagesErreursSemantiques getInstance() {
        return ourInstance;
    }
}
