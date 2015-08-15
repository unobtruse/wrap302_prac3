package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/10.
 */
public class GameScores extends Activity {
    private Game game = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_scores);
        List<Player> players = new ArrayList<>();
        Intent intent = getIntent();
        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                game = (Game) extras.getSerializable("game");
                players = initPlayers(game);
            }
        }

        ListView listRanks = (ListView) findViewById(R.id.listRanks);
        PlayerAdapter adapter = new PlayerAdapter(this, players);
        listRanks.setAdapter(adapter);
        createButtonListeners();
    }

    private List<Player> initPlayers(Game game) {
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        return players;
    }

    private void createButtonListeners() {
        Button btnStats = (Button) findViewById(R.id.btnStats);
        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameScores.this.toStats();
            }
        });
    }

    private void toStats() {
        Intent intent = new Intent(this, Stats.class);
        intent.putExtra("game", game);
        startActivity(intent);
        finish();
    }
}