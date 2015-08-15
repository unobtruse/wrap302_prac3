package nmmu.wrap302.prac3.task2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/07.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.contact_layout, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View contactView = convertView;
        if(contactView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contactView = inflater.inflate(R.layout.contact_layout, parent, false);
        }
        Contact contact = getItem(position);
        contactView.setTag(contact);
        TextView txtName = (TextView) contactView.findViewById(R.id.name);
        TextView txtCell = (TextView) contactView.findViewById(R.id.cell);
        TextView txtEmail = (TextView) contactView.findViewById(R.id.email);
        Button btnClose = (Button) contactView.findViewById(R.id.close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactAdapter.this.remove(contact);
                ContactAdapter.this.notifyDataSetChanged();
            }
        });
        txtName.setText(String.format("%s %s", contact.name, contact.surname));
        txtCell.setText(contact.cell);
        txtEmail.setText(contact.email);
        return contactView;
    }
}
