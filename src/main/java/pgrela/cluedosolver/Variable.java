package pgrela.cluedosolver;

public class Variable {
    Player player;
    Card card;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Variable(Player player, Card card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (card != null ? !card.equals(variable.card) : variable.card != null) return false;
        if (player != null ? !player.equals(variable.player) : variable.player != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (card != null ? card.hashCode() : 0);
        return result;
    }
}
