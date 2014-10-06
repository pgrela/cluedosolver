package pgrela.cluedosolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class CluedoSolverTest {
    @Test
    public void shouldGiveProperAnswer() {
        Player anna = new Player("Anna");
        Player bartek = new Player("Bartek");
        Player cecily = new Player("Cecylia");
        List<Player> players = Lists.newArrayList(anna, bartek, cecily);

        List<Card> cards = getSmallSetOfCards();

        CluedoSolver cluedoSolver = new CluedoSolverEquationMatrix(players, cards);
        cluedoSolver.setMyCards(anna, getMyCard() );

        cluedoSolver.addSuggestion(new Suggestion(
                new Answer(
                        CardRoom.KITCHEN,
                        CardPerson.ZIELNICKI,
                        CardItem.MLOTEK),
                anna)
                .refutedBy(bartek,CardItem.MLOTEK)
        );
        cluedoSolver.addSuggestion(new Suggestion(
                new Answer(
                        CardRoom.SPA,
                        CardPerson.ZIELNICKI,
                        CardItem.MLOTEK),
                bartek)
                .refutedBy(cecily,CardRoom.SPA)
        );

        Answer answer = cluedoSolver.calculateAnswer();

        AnswerAssertion.assertThat(answer).hasItem(CardItem.NOZ).hasPerson(CardPerson.ZIELNICKI).hasRoom(CardRoom.KITCHEN);
    }

    private List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(Arrays.asList(CardItem.class.getEnumConstants()));
        cards.addAll(Arrays.asList(CardPerson.class.getEnumConstants()));
        cards.addAll(Arrays.asList(CardRoom.class.getEnumConstants()));
        return cards;
    }
    private List<Card> getSmallSetOfCards(){
        List<Card> cards = new ArrayList<>();
        cards.add(CardPerson.SIWECKA);
        cards.add(CardPerson.ZIELNICKI);
        cards.add(CardRoom.SPA);
        cards.add(CardRoom.KITCHEN);
        cards.add(CardItem.MLOTEK);
        cards.add(CardItem.NOZ);
        return cards;
    }
    private List<Card> getMyCard(){
        List<Card> cards = new ArrayList<>();
        cards.add(CardPerson.SIWECKA);
        return cards;
    }
}
