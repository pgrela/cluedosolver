package pgrela.cluedosolver;

public class Suggestion {
    private Answer answer;
    private Player suggestingPlayer;
    private Player refutingPlayer;
    private Card shownCard;

    public Suggestion(Answer answer, Player suggestingPlayer) {
        this.answer = answer;
        this.suggestingPlayer = suggestingPlayer;
    }

    public Suggestion refutedBy(Player refutingPlayer, Card shownCard) {
        this.refutingPlayer = refutingPlayer;
        this.shownCard = shownCard;
        return this;
    }

    public Suggestion refutedBy(Player refutingPlayer) {
        this.refutingPlayer = refutingPlayer;
        this.shownCard = null;
        return this;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Player getSuggestingPlayer() {
        return suggestingPlayer;
    }

    public Player getRefutingPlayer() {
        return refutingPlayer;
    }

    public Card getShownCard() {
        return shownCard;
    }
}
