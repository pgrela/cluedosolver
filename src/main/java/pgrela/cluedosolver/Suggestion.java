package pgrela.cluedosolver;

public class Suggestion {
    private Answer answer;
    private Player suggestingPlayer;
    private Player refutingPlayer;
    private Card shownCard;

    public Suggestion(Answer answer, Player suggestingPlayer, Player refutingPlayer, Card shownCard) {
        this.answer = answer;
        this.suggestingPlayer = suggestingPlayer;
        this.refutingPlayer = refutingPlayer;
        this.shownCard = shownCard;
    }
    public Suggestion(Answer answer, Player suggestingPlayer, Player refutingPlayer) {
        this(answer,suggestingPlayer,refutingPlayer, null);
    }
    public Suggestion(Answer answer, Player suggestingPlayer) {
        this(answer,suggestingPlayer,null, null);
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
