package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author minnaar
 * @since 2015/08/10.
 */
public class Play extends Activity {
    private Game game;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        Intent intent = getIntent();
        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                game = (Game) extras.getSerializable("game");
                refreshLabels();
                refreshColor();
            }
        }
        createButtonListeners();
    }

    private void createButtonListeners() {
        Button btnRoll = (Button) findViewById(R.id.btnRoll);
        Button btnHold = (Button) findViewById(R.id.btnHold);
        Button btnWinner = (Button) findViewById(R.id.btnWinner);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Play.this.throwDice();
            }
        });
        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Play.this.holdRound();
            }
        });
        btnWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Play.this.toGameScores();
            }
        });
    }

    private void throwDice() {
        int[] dice = new int[2];
        dice[0] = dice[1] = 1;
        Outcome outcome = game.getCurPlayer().throwDice(dice);
        ImageView imgDie1 = (ImageView) findViewById(R.id.die1);
        ImageView imgDie2 = (ImageView) findViewById(R.id.die2);
        imgDie1.setImageResource(getResources().getIdentifier(
                String.format("d%d", dice[0]),
                "drawable",
                getPackageName()
        ));
        imgDie2.setImageResource(getResources().getIdentifier(
                String.format("d%d", dice[1]),
                "drawable",
                getPackageName()
        ));
        refreshLabels();
        switch(outcome) {
            case MUST_ROLL:
                setHoldEnabled(false);
                setRollEnabled(true);
                break;
            case CAN_ROLL:
                setHoldEnabled(true);
                setRollEnabled(true);
                checkWin();
                break;
            case CANT_ROLL:
                setHoldEnabled(true);
                setRollEnabled(false);
                break;
        }
    }

    private void checkWin() {
        if(game.getCurPlayer().isWinner()) {
            ((Button)findViewById(R.id.btnHold)).setVisibility(View.INVISIBLE);
            ((Button)findViewById(R.id.btnRoll)).setVisibility(View.INVISIBLE);
            ((Button)findViewById(R.id.btnWinner)).setVisibility(View.VISIBLE);
            setupWinLabels();
        }
    }

    private void setupWinLabels() {
        TextView lblInfo = (TextView) findViewById(R.id.lblInfo);
        lblInfo.setText(String.format(
                "%s %d %s!",
                getString(R.string.player),
                game.getCurPlayer().getNumber(),
                getString(R.string.wins)
        ));
    }

    private void toGameScores() {
        Intent intent = new Intent(this, GameScores.class);
        Intent intent2 = new Intent(this, Pig.class);
        intent.putExtra("game", game);
        intent2.putExtra("game", game);
        startActivity(intent2);
        startActivity(intent);
        finish();
    }

    private void setRollEnabled(boolean value) {
        Button btnRoll = (Button) findViewById(R.id.btnRoll);
        btnRoll.setEnabled(value);
    }

    private void setHoldEnabled(boolean value) {
        Button btnHold = (Button) findViewById(R.id.btnHold);
        btnHold.setEnabled(value);
    }

    private void holdRound() {
        game.getCurPlayer().nextRound();
        game.nextPlayer();
        setHoldEnabled(true);
        setRollEnabled(true);
        refreshLabels();
        refreshColor();
    }
    private void refreshColor() {
        RelativeLayout playContainer = (RelativeLayout) findViewById(R.id.playContainer);
        playContainer.setBackgroundColor(game.getCurPlayer().getColor());
    }
    private void refreshLabels() {
        TextView lblInfo = (TextView) findViewById(R.id.lblInfo);
        TextView lblScore = (TextView) findViewById(R.id.lblScore);
        TextView lblRank = (TextView) findViewById(R.id.lblRank);
        lblInfo.setText(String.format("%s %d's Turn",
                getString(R.string.player),
                game.getCurPlayer().getNumber()
        ));
        lblScore.setText(String.format("%s %s: %d %s %s: %d",
                getString(R.string.round),
                getString(R.string.score),
                game.getCurPlayer().getCurRound().sum,
                getString(R.string.total),
                getString(R.string.score),
                game.getCurPlayer().roundsSum
        ));
        lblRank.setText(String.format("%s: %d",
                getString(R.string.rank),
                game.getCurPlayer().rank
        ));
    }
}