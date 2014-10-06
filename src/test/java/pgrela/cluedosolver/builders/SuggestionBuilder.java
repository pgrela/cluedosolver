package pgrela.cluedosolver.builders;

import pgrela.cluedosolver.Answer;
import pgrela.cluedosolver.Card;
import pgrela.cluedosolver.CardItem;
import pgrela.cluedosolver.CardPerson;
import pgrela.cluedosolver.CardRoom;
import pgrela.cluedosolver.Player;
import pgrela.cluedosolver.Suggestion;

public class SuggestionBuilder {
    private Answer answer;
    private Player suggestingPlayer;
    private Player refutingPlayer;
    private Card shownCard;

    public static SuggestionBuilder aSuggestion() {
        return new SuggestionBuilder();
    }

    public SuggestionBuilder withAnswer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public SuggestionBuilder withAnswer(CardRoom room, CardPerson person, CardItem item) {
        this.answer = new Answer(room, person, item);
        return this;
    }

    public SuggestionBuilder withSuggestingPlayer(Player suggestingPlayer) {
        this.suggestingPlayer = suggestingPlayer;
        return this;
    }

    public SuggestionBuilder withRefutingPlayer(Player refutingPlayer) {
        this.refutingPlayer = refutingPlayer;
        return this;
    }

    public SuggestionBuilder withShownCard(Card shownCard) {
        this.shownCard = shownCard;
        return this;
    }

    public Suggestion create() {
        return new Suggestion(answer, suggestingPlayer, refutingPlayer, shownCard);
    }
}
