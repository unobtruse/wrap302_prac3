package nmmu.wrap302.prac3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @author minnaar
 * @since 2015/08/11.
 */
public class GameAdapter extends ArrayAdapter<Game> {
    private Context context;
    public GameAdapter(Context context, List<Game> games) {
        super(context, R.layout.item, games);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gameView = convertView;
        if(gameView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gameView = inflater.inflate(R.layout.item, parent, false);
        }
        Game game = getItem(position);
        gameView.setTag(game);
        setupLabels(gameView, position);
        setupListener(gameView);
        return gameView;
    }

    private void setupLabels(View gameView, int position) {
        TextView lblHeading = (TextView) gameView.findViewById(R.id.lblHeading);
        TextView lblSub = (TextView) gameView.findViewById(R.id.lblSub);
        Game game = (Game) gameView.getTag();
        lblHeading.setText(String.format(
                "%s %d",
                getContext().getString(R.string.game),
                position + 1
        ));
        lblSub.setText(String.format(
                "%s: %d %s: %d",
                getContext().getString(R.string.numPlayers),
                game.getPlayers().size(),
                getContext().getString(R.string.rounds),
                game.getAllRounds().size()
        ));
    }

    public void setupListener(View gameView) {
        gameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGameScores((Game)gameView.getTag());
            }
        });
    }

    public void toGameScores(Game game) {
        Intent intent = new Intent(context, GameScores.class);
        intent.putExtra("game", game);
        context.startActivity(intent);
    }
}
