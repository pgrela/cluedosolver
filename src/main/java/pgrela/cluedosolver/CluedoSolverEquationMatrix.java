package pgrela.cluedosolver;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Jama.Matrix;

public class CluedoSolverEquationMatrix implements CluedoSolver {
    Map<Variable, Integer> cardsToIndex;
    ArrayList<Player> players;
    ArrayList<Card> cards;
    Player resultPlayer = new Player("Result");

    int equationLength;

    List<Equation> equations = new ArrayList<>();

    public CluedoSolverEquationMatrix(List<Player> players, List<Card> cards) {
        this.players = new ArrayList<>(players);
        this.cards = new ArrayList<>(cards);
        this.players.add(resultPlayer);
        equationLength = this.players.size() * this.cards.size();
        createCardsIndex(this.cards);
        setupInitialEquations(this.cards, this.players);
        setSolutionCardsEquations(this.cards, resultPlayer);
        setPlayersCountCards(players, (cards.size() - 3) / players.size());
    }

    private void setPlayersCountCards(Collection<Player> players, int handSize) {
        for (Player player : players) {
            Equation equation = createEquation("Player " + player + " has exactly " + handSize + " cards");
            for (Card card : cards) {
                setVariableParameter(equation, player, card, 1.0);
            }
            equation.setSolution((double) handSize);
            equations.add(equation);
        }
    }

    private void setSolutionCardsEquations(ArrayList<Card> cards, Player resultPlayer) {
        createSolutionEquationsForCardType(cards, resultPlayer, CardPerson.class);
        createSolutionEquationsForCardType(cards, resultPlayer, CardItem.class);
        createSolutionEquationsForCardType(cards, resultPlayer, CardRoom.class);
    }

    private void createSolutionEquationsForCardType(ArrayList<Card> cards, Player resultPlayer, Class cardPersonClass) {
        Equation equation = createEquation(
                "There is only one card of type " + cardPersonClass.getSimpleName() + " in " +
                        "solution");
        for (Card card : cards) {
            if (cardPersonClass.isInstance(card)) {
                setVariableParameter(equation, resultPlayer, card, 1.0);
            }
        }
        equation.setSolution(1.0);
        equations.add(equation);
    }

    @Override
    public void setMyCards(Player myself, Collection<Card> cards) {
        for (Card card : this.cards) {
            if (cards.contains(card)) {
                Equation equation = createEquation("I have " + card);
                equation.setSolution(1.0);
                setVariableParameter(equation, myself, card, 1.0);
                equations.add(equation);
                otherPlayersDontHave(myself, card);
            } else {
                Equation equation = createEquation("I don't have " + card);
                equation.setSolution(0.0);
                setVariableParameter(equation, myself, card, 1.0);
                equations.add(equation);
            }
        }
    }

    private void otherPlayersDontHave(Player theOne, Card card) {
        for (Player player : players) {
            if (!player.equals(theOne)) {
                Equation equation = createEquation("The others, so " + theOne + " don't have " + card);
                setVariableParameter(equation, player, card, 1.0);
                equation.setSolution(0.0);
                equations.add(equation);
            }
        }
    }

    private void setupInitialEquations(List<Card> cards, List<Player> players) {
        for (Card card : cards) {
            Equation equation = createEquation("Some player has " + card);
            for (Player player : players) {
                setVariableParameter(equation, player, card, 1.0);
            }
            equation.setSolution(1.0);
            equations.add(equation);
        }
    }

    private void createCardsIndex(List<Card> cards) {
        cardsToIndex = new HashMap<>();
        int variableIndex = 0;
        for (Card card : cards) {
            for (Player player : players) {
                cardsToIndex.put(new Variable(player, card), variableIndex);
                ++variableIndex;
            }
        }
    }

    @Override
    public void addSuggestion(Suggestion suggestion) {
        // Answer:missed by Person
        // Answer:Answered by Person
        Player playerTryingToRefuteSuggestion = nextPlayer(suggestion.getSuggestingPlayer());
        Player refutingPlayer = suggestion.getRefutingPlayer();
        while (
                !playerTryingToRefuteSuggestion.equals(refutingPlayer)
                        && !playerTryingToRefuteSuggestion.equals(suggestion.getSuggestingPlayer())) {
            playerHasNoneOf(playerTryingToRefuteSuggestion, suggestion.getAnswer());
            playerTryingToRefuteSuggestion = nextPlayer(playerTryingToRefuteSuggestion);
        }
        if (suggestion.getRefutingPlayer() != null) {
            if (suggestion.getShownCard() == null) {
                playersHasOneOf(refutingPlayer, suggestion.getAnswer());
            } else {
                playerHasTheCard(refutingPlayer, suggestion.getShownCard());
            }
        }
    }

    private void playersHasOneOf(Player refutingPlayer, Answer answer) {
        Equation equation = transformAnswerToEquation(refutingPlayer + " refuted to " + answer + " with unknown card",
                refutingPlayer,
                answer);
        equation.setSolution(1.0);
        equations.add(equation);
    }

    private void playerHasTheCard(Player playerTryingToRefuteSuggestion, Card shownCard) {
        Equation equation = createEquation(
                playerTryingToRefuteSuggestion + " refuted to suggestion with card " + shownCard);
        setVariableParameter(equation, playerTryingToRefuteSuggestion, shownCard, 1.0);
        equation.setSolution(1.0);
        equations.add(equation);
        otherPlayersDontHave(playerTryingToRefuteSuggestion, shownCard);
    }

    private void playerHasNoneOf(Player refutingPlayer, Answer answer) {
        Equation equation = transformAnswerToEquation(refutingPlayer + " passed to " + answer + "", refutingPlayer,
                answer);
        equation.setSolution(0.0);
        equations.add(equation);
    }

    private Equation transformAnswerToEquation(String equationName, Player player, Answer answer) {
        Equation equation = createEquation(equationName);
        setVariableParameter(equation, player, answer.getItem(), 1.0);
        setVariableParameter(equation, player, answer.getRoom(), 1.0);
        setVariableParameter(equation, player, answer.getPerson(), 1.0);
        return equation;
    }

    private Equation createEquation(String name) {
        return new Equation(name, equationLength);
    }

    private void setVariableParameter(Equation equation, Player player, Card card, Double parameter) {
        equation.setParam(cardsToIndex.get(new Variable(player, card)), parameter);
    }

    private Player nextPlayer(Player playerTryingToRefuteSuggestion) {
        return players.get((players.indexOf(playerTryingToRefuteSuggestion) + 1) % players.size());
    }

    @Override
    public Answer calculateAnswer() {
        double[][] matrix = new double[equations.size()][equationLength];
        double[] results = new double[equations.size()];
        for (int i = 0; i < equations.size(); ++i) {
            for (int j = 0; j < equationLength; j++) {
                matrix[i][j] = doubleOrZero(i, j);
            }
            results[i] = equations.get(i).getSolution();
        }
        Matrix m = new Matrix(matrix);
        //printMatrix(matrix);
        Matrix b = new Matrix(results, 1).transpose();
        Matrix solution;
        try{
            solution = m.solve(b);
        }catch (RuntimeException e){
            return null;
        }
        Answer theAnswer = new Answer();
        for(Map.Entry<Variable,Integer> entry:cardsToIndex.entrySet()){
            if(solution.get(entry.getValue(),0)>0.5 && entry.getKey().getPlayer().equals(resultPlayer)){
                theAnswer.setCard(entry.getKey().getCard());
            }
        }
        return theAnswer;
    }

    private double doubleOrZero(int i, int j) {
        return equations.get(i).getParam(j);
    }

    private void printMatrix(double[][] M){
        for(double [] row:M){
            for(double i : row){
                System.out.print((int)(i+.5) + " ");
            }
            System.out.println("");
        }
    }
}
