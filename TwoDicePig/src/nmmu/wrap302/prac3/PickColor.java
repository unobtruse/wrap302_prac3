package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author minnaar
 * @since 2015/08/10.
 */
public class PickColor extends Activity {
    private Game game = null;
    private int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_color);
        Intent intent = getIntent();
        final TextView lblPlayerColor = (TextView) findViewById(R.id.lblPlayerColor);
        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                game = (Game) extras.getSerializable("game");
                nextPlayer(lblPlayerColor);
            }
        }
        SeekBar barRed = (SeekBar) findViewById(R.id.barRed);
        SeekBar barGreen = (SeekBar) findViewById(R.id.barGreen);
        SeekBar barBlue = (SeekBar) findViewById(R.id.barBlue);

        barRed.setOnSeekBarChangeListener(getBarListener());
        barGreen.setOnSeekBarChangeListener(getBarListener());
        barBlue.setOnSeekBarChangeListener(getBarListener());

        Button btnNextColor = (Button) findViewById(R.id.btnNextColor);
        btnNextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.isNextLast()) {
                    setPlayerColor();
                    nextPlayer(lblPlayerColor);
                    setColor(0,0,0);
                } else {
                    setPlayerColor();
                    game.nextPlayer();
                    startPlay();
                }
            }
        });
    }

    private void resetBars() {
        SeekBar barRed = (SeekBar) findViewById(R.id.barRed);
        SeekBar barGreen = (SeekBar) findViewById(R.id.barGreen);
        SeekBar barBlue = (SeekBar) findViewById(R.id.barBlue);
        resetBar(barRed, barGreen, barBlue);
    }

    private void resetBar(SeekBar... bars) {
        for(SeekBar bar : bars)
            bar.setProgress(0);
    }

    private SeekBar.OnSeekBarChangeListener getBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()) {
                    case R.id.barBlue:
                        setColor(Color.red(color), Color.green(color), progress);
                        break;
                    case R.id.barGreen:
                        setColor(Color.red(color), progress, Color.blue(color));
                        break;
                    case R.id.barRed:
                        setColor(progress, Color.green(color), Color.blue(color));
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
    }

    private void nextPlayer(TextView label) {
        game.nextPlayer();
        resetBars();
        label.setText(String.format("%s %d %s",
                getString(R.string.player),
                game.getCurPlayer().getNumber(),
                getString(R.string.color)
        ));
    }

    private void setColor(int r, int g, int b) {
        color = Color.argb(255, r, g, b);
        View container = (View) findViewById(R.id.pickColorContainer);
        container.setBackgroundColor(color);
    }

    private void setPlayerColor() {
        game.getCurPlayer().setColor(color);
    }

    private void startPlay() {
        Intent intent = new Intent(this, Play.class);
        intent.putExtra("game", game);
        startActivity(intent);
        finish();
    }
}