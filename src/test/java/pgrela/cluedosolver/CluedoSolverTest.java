package pgrela.cluedosolver;

import static pgrela.cluedosolver.builders.SuggestionBuilder.aSuggestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class CluedoSolverTest {
    @Test
    public void shouldGiveProperAnswer() {
        Player anna = new Player("Anna");
        Player bill = new Player("Bill");
        Player cecily = new Player("Cecylia");
        List<Player> players = Lists.newArrayList(anna, bill, cecily);

        List<Card> cards = getSmallSetOfCards();

        CluedoSolver cluedoSolver = new CluedoSolverEquationMatrix(players, cards);
        cluedoSolver.setMyCards(anna, getMyCard());

        cluedoSolver.addSuggestion(
                aSuggestion().withSuggestingPlayer(anna).withAnswer(
                        CardRoom.KITCHEN,
                        CardPerson.ZIELNICKI,
                        CardItem.MLOTEK
                ).withRefutingPlayer(bill).withShownCard(CardItem.MLOTEK).create()
        );
        cluedoSolver.addSuggestion(
                aSuggestion().withSuggestingPlayer(anna).withAnswer(
                        CardRoom.SPA,
                        CardPerson.ZIELNICKI,
                        CardItem.MLOTEK
                ).withRefutingPlayer(cecily).withShownCard(CardRoom.SPA).create()
        );

        Answer answer = cluedoSolver.calculateAnswer();

        AnswerAssertion.assertThat(answer).hasItem(CardItem.NOZ).hasPerson(CardPerson.ZIELNICKI).hasRoom(
                CardRoom.KITCHEN);
    }

    private List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(Arrays.asList(CardItem.class.getEnumConstants()));
        cards.addAll(Arrays.asList(CardPerson.class.getEnumConstants()));
        cards.addAll(Arrays.asList(CardRoom.class.getEnumConstants()));
        return cards;
    }

    private List<Card> getSmallSetOfCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(CardPerson.SIWECKA);
        cards.add(CardPerson.ZIELNICKI);
        cards.add(CardRoom.SPA);
        cards.add(CardRoom.KITCHEN);
        cards.add(CardItem.MLOTEK);
        cards.add(CardItem.NOZ);
        return cards;
    }

    private List<Card> getMyCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(CardPerson.SIWECKA);
        return cards;
    }

}
