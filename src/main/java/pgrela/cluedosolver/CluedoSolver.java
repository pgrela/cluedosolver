package pgrela.cluedosolver;

import java.util.Collection;

public interface CluedoSolver {
    void setMyCards(Player myself, Collection<Card> cards);

    void addSuggestion(Suggestion suggestion);

    Answer calculateAnswer();
}
