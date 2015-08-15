package nmmu.wrap302.prac3.task2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author minnaar
 * @since 2015/08/14.
 */
public class EditFragment extends Fragment {
    private EditText editName, editSurname, editCell, editEmail;
    private int position = -1;
    private Contact contact = new Contact();
    private GetContactListener getContactListener;
    private OnApplyClickListener applyClickListener;

    public interface GetContactListener {
        public Contact getContact(int position);
    }

    public interface OnApplyClickListener {
        public void onApplyClick(int position, Contact contact);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            getContactListener = (GetContactListener) activity;
            applyClickListener = (OnApplyClickListener) activity;
        } catch (ClassCastException e) {
            Log.e(getClass().getName(), "Activity does not implement interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_edit, container, false);
        setupButtonListener(view);
        initEditTexts(view);
        initContact();
        return view;
    }

    private void setupButtonListener(View view) {
        Button btnApply = (Button) view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
                applyClickListener.onApplyClick(position, contact);
            }
        });
    }

    private void initContact() {
        if(position != -1)
            contact = getContactListener.getContact(position);
        populateFields();
    }

    private void initEditTexts(View view) {
        editName = (EditText) view.findViewById(R.id.editName);
        editSurname = (EditText) view.findViewById(R.id.editSurname);
        editCell = (EditText) view.findViewById(R.id.editCell);
        editEmail = (EditText) view.findViewById(R.id.editEmail);
    }

    public void setPosition(int position) {
        this.position = position;
        if(getContactListener != null)
            initContact();
    }

    private void populateFields() {
        editName.setText(contact.name);
        editSurname.setText(contact.surname);
        editCell.setText(contact.cell);
        editEmail.setText(contact.email);
    }

    private void updateContact() {
        contact.cell = editCell.getText().toString();
        contact.email = editEmail.getText().toString();
        contact.name = editName.getText().toString();
        contact.surname = editSurname.getText().toString();
    }
}