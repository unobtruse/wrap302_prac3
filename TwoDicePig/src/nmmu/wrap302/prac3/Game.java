package nmmu.wrap302.prac3;

import java.io.Serializable;
import java.util.*;

/**
 * @author minnaar
 * @since 2015/08/09.
 */
public class Game implements Serializable, Round.RoundChangeListener {
    private List<Player> players;
    private int curPlayer = -1;
    public int roundsSum = 0;
    public int roundsMin = 0;
    public int roundsMax = 0;
    public double roundsAvg = 0;
    public Game() {
        players = new ArrayList<>();
    }
    public void addPlayer(Player player) {
        players.add(player);
        nextPlayer();
    }
    public int getNumberCount(int number) {
        int sum = 0;
        for(Round round: getAllRounds())
            sum += round.getCounts()[number - 1];
        return sum;
    }
    public List<Player> getPlayers() {return players;}
    public void nextPlayer() {
        if(!isNextLast())
            curPlayer++;
        else
            curPlayer = 0;
    }
    public boolean isNextLast() {
        return curPlayer + 1 >= players.size();
    }
    public Player getCurPlayer() {
        if(players.size() > 0)
            return players.get(curPlayer);
        return null;
    }
    public Set<Round> getAllRounds() {
        Set<Round> rounds = new HashSet<Round>();
        for(Player player : players) {
            rounds.addAll(player.getRounds());
        }
        return rounds;
    }
    private void updateRoundsMax(Set<Round> rounds) {
        roundsMax = getCurPlayer().getCurRound().sum;
        for(Round round: rounds) {
            if(round.sum > roundsMax)
                roundsMax = round.sum;
        }
    }
    private void updateRoundsMin(Set<Round> rounds) {
        roundsMin = getCurPlayer().getCurRound().sum;
        for(Round round: rounds) {
            if(round.sum < roundsMin)
                roundsMin = round.sum;
        }
    }
    private void updateRoundsSum(Set<Round> rounds) {
        roundsSum = 0;
        for(Round round: rounds)
            roundsSum += round.sum;
    }

    private void refreshRanks() {
        ArrayList<Player> sortedPlayers = new ArrayList<>();
        sortedPlayers.addAll(players);
        Collections.sort(sortedPlayers);
        int counter = 0;
        int rank = 1;
        while(counter < sortedPlayers.size()) {
            Player cur = sortedPlayers.get(counter);
            cur.rank = rank;
            if(counter + 1 < sortedPlayers.size())
                if(cur.compareTo(sortedPlayers.get(counter + 1)) != 0)
                    rank++;
            counter++;
        }
    }

    private void updateRoundsAvg(Set<Round> rounds) {
        roundsAvg = (double) roundsSum/rounds.size();
    }

    @Override
    public void onRoundChange() {
        Set<Round> rounds = getAllRounds();
        if(rounds.size() > 0) {
            updateRoundsSum(rounds);
            updateRoundsMax(rounds);
            updateRoundsMin(rounds);
            updateRoundsAvg(rounds);
            refreshRanks();
        }
    }
}
