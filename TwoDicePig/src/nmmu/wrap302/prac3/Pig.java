package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/09.
 */
public class Pig extends Activity {
    private List<Game> games;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        games = new ArrayList<Game>();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras != null) {
            Game game = (Game) extras.getSerializable("game");
            games.add(game);
        }
    }

    public void onStartNumPlayers(View view) {
        Game game = new Game();
        Intent intent = new Intent(this, NumPlayers.class);
        intent.putExtra("game", game);
        startActivity(intent);
    }

    public void onStartResults(View view) {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("games", (ArrayList<Game>)games);
        startActivity(intent);
    }
}