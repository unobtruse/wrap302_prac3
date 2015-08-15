package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author minnaar
 * @since 2015/08/11.
 */
public class Stats extends Activity {
    private Game game;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        Intent intent = getIntent();
        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                game = (Game) extras.getSerializable("game");
                setupLabels();
            }
        }
    }

    private void setupLabels() {
        TextView lblRounds = (TextView) findViewById(R.id.lblRounds);
        TextView lblMinScore = (TextView) findViewById(R.id.lblMinScore);
        TextView lblMaxScore = (TextView) findViewById(R.id.lblMaxScore);
        TextView lblAvgScore = (TextView) findViewById(R.id.lblAvgScore);
        TextView lblOnes = (TextView) findViewById(R.id.lblOnes);
        TextView lblTwos = (TextView) findViewById(R.id.lblTwos);
        TextView lblThrees = (TextView) findViewById(R.id.lblThrees);
        TextView lblFours = (TextView) findViewById(R.id.lblFours);
        TextView lblFives = (TextView) findViewById(R.id.lblFives);
        TextView lblSixes = (TextView) findViewById(R.id.lblSixes);
        lblRounds.setText(Integer.toString(game.getAllRounds().size()));
        lblMinScore.setText(Integer.toString(game.roundsMin));
        lblMaxScore.setText(Integer.toString(game.roundsMax));
        lblAvgScore.setText(String.format("%.2f", game.roundsAvg));
        lblOnes.setText(Integer.toString(game.getNumberCount(1)));
        lblTwos.setText(Integer.toString(game.getNumberCount(2)));
        lblThrees.setText(Integer.toString(game.getNumberCount(3)));
        lblFours.setText(Integer.toString(game.getNumberCount(4)));
        lblFives.setText(Integer.toString(game.getNumberCount(5)));
        lblSixes.setText(Integer.toString(game.getNumberCount(6)));
    }
}