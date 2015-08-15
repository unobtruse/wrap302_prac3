package nmmu.wrap302.prac3;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author minnaar
 * @since 2015/08/09.
 */
public class Player implements Serializable, Comparable<Player>, Round.RoundChangeListener {
    private static final int ROUND_WIN = 100;
    private int color;
    private int number;
    public int rank = 1;
    public int roundsSum = 0;
    private Set<Round> rounds = new HashSet<Round>();
    private Round round;
    private Round.RoundChangeListener listener;
    private Random random = new Random();

    public Player(int number, Round.RoundChangeListener listener) {
        this.listener = listener;
        this.number = number;
        round = new Round(this);
    }
    public int getNumber() {return number;}
    public void setColor(int color) {
        this.color = color;
    }
    public int getColor() {return color;}
    public Outcome throwDice(int[] dice) {
        dice[0] = random.nextInt(6) + 1;
        dice[1] = random.nextInt(6) + 1;
        round.addNumber(dice[0], dice[1]);
        Outcome outcome = Outcome.CAN_ROLL;
        if(dice[0] == 1 && dice[1] == 1) {
            resetRounds();
            round.resetCounts();
            nextRound();
            outcome = Outcome.CANT_ROLL;
        }
        if(dice[0] == 1 || dice[1] == 1) {
            round.resetCounts();
            nextRound();
            outcome = Outcome.CANT_ROLL;
        }
        if((dice[0] == dice[1]) && dice[0] != 1) {
            outcome = Outcome.MUST_ROLL;
        }
        return outcome;
    }

    private void updateRoundsSum() {
        roundsSum = 0;
        for(Round round: rounds)
            roundsSum += round.sum;
    }
    public boolean isWinner() {
        return round.sum + roundsSum >= ROUND_WIN;
    }
    public void nextRound() {
        rounds.add(round);
        this.round = new Round(this);
    }
    public Round getCurRound() {return  round;}
    public Set<Round> getRounds() {return rounds;}

    private void resetRounds() {
        for(Round round: rounds)
            round.resetCounts();
    }

    @Override
    public int compareTo(Player another) {
        return  another.roundsSum - this.roundsSum;
    }

    @Override
    public void onRoundChange() {
        updateRoundsSum();
        listener.onRoundChange();
    }
}
