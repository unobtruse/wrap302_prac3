package nmmu.wrap302.prac3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/11.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdapter(Context context, List<Player> players) {
        super(context, R.layout.item, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View playerView = convertView;
        if(playerView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            playerView = inflater.inflate(R.layout.item, parent, false);
        }
        Player player = getItem(position);
        playerView.setTag(player);
        setupLabels(playerView);
        return playerView;
    }

    private void setupLabels(View playerView) {
        TextView lblHeading = (TextView) playerView.findViewById(R.id.lblHeading);
        TextView lblSub = (TextView) playerView.findViewById(R.id.lblSub);
        Player player = (Player)playerView.getTag();
        lblHeading.setText(String.format(
                "%s %d",
                getContext().getString(R.string.place),
                player.rank
        ));
        lblSub.setText(String.format(
                "%s %d",
                getContext().getString(R.string.player),
                player.getNumber()
        ));
    }
}
