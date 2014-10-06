package pgrela.cluedosolver;

public class Answer {
    CardPerson person;
    CardItem item;
    CardRoom room;

    public Answer(CardRoom room, CardPerson person, CardItem item) {
        this.person = person;
        this.item = item;
        this.room = room;
    }

    public Answer() {

    }

    @Override
    public String toString() {
        return "["+person+", "+item+", "+room+"]";
    }

    public CardPerson getPerson() {
        return person;
    }

    public CardItem getItem() {
        return item;
    }

    public CardRoom getRoom() {
        return room;
    }
    public void setCard(Card card){
        if(card instanceof CardPerson){
            person = (CardPerson) card;
        }
        if(card instanceof CardRoom){
            room = (CardRoom) card;
        }
        if(card instanceof CardItem){
            item = (CardItem) card;
        }
    }
}
