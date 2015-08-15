package nmmu.wrap302.prac3;

import java.io.Serializable;

/**
 * @author minnaar
 * @since 2015/08/09.
 */
public class Round implements Serializable {
    public interface RoundChangeListener {
        public void onRoundChange();
    }
    private int[] counts;
    public int sum = 0;
    private RoundChangeListener listener;
    public Round(RoundChangeListener listener) {
        this.listener = listener;
        counts = new int[6];
        resetCounts();
    }
    public void resetCounts() {
        sum = 0;
        listener.onRoundChange();
    }
    public void addNumber(int... numbers) {
        for(int n : numbers) {
            counts[n - 1]++;
            sum += n;
        }
        listener.onRoundChange();
    }

    public int[] getCounts() {return counts;}
}
