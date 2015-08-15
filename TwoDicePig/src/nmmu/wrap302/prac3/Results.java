package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/10.
 */
public class Results extends Activity {
    private List<Game> games = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Intent intent = getIntent();
        if(intent != null) {
            Bundle extras = intent.getExtras();
            if(extras != null) {
                games = (ArrayList<Game>) extras.getSerializable("games");
            }
        }
        ListView listGames = (ListView) findViewById(R.id.listGames);
        GameAdapter adapter = new GameAdapter(this, games);
        listGames.setAdapter(adapter);
    }
}