package pgrela.cluedosolver;

public class AnswerAssertion extends org.assertj.core.api.Assertions{
    Answer answer;
    public AnswerAssertion(Answer answer) {
        this.answer=answer;
    }
    public static AnswerAssertion assertThat(Answer actual){
        return new AnswerAssertion(actual);
    }
    public AnswerAssertion hasRoom(CardRoom room){
        assertThat(answer.getRoom()).isEqualTo(room);
        return this;
    }
    public AnswerAssertion hasPerson(CardPerson person){
        assertThat(answer.getPerson()).isEqualTo(person);
        return this;
    }
    public AnswerAssertion hasItem(CardItem item){
        assertThat(answer.getItem()).isEqualTo(item);
        return this;
    }
}
