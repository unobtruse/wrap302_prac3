package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * @author minnaar
 * @since 2015/08/10.
 */
public class NumPlayers extends Activity {
    private Game game = null;
    private int numPlayers = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.num_players);

        Intent intent = getIntent();

        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                game = (Game) extras.getSerializable("game");
            }
        }

        NumberPicker picker = (NumberPicker) findViewById(R.id.numberPicker);
        picker.setMinValue(numPlayers);
        picker.setMaxValue(10);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setPlayers(newVal);
            }
        });

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGame();
            }
        });
    }

    private void setPlayers(int val) {
        numPlayers = val;
    }

    private void initGame() {
        populatePlayers();
        Intent intent = new Intent(this, PickColor.class);
        intent.putExtra("game", game);
        startActivity(intent);
        finish();
    }

    private void populatePlayers() {
        for(int i = 0; i < numPlayers; i++) {
            game.addPlayer(new Player(i + 1, game));
        }
    }
}