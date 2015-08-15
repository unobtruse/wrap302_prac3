package nmmu.wrap302.prac3.task2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author minnaar
 * @since 2015/08/06.
 */
public class Main extends Activity
    implements ContactsFragment.OnContactSelectedListener,
        ContactsFragment.OnContactsSortClickListener,
        ContactsFragment.OnContactAddClickListener,
        EditFragment.GetContactListener,
        EditFragment.OnApplyClickListener
{
    private List<Contact> contacts;
    private ContactAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initContacts();
        adapter = new ContactAdapter(this, contacts);
        FragmentManager manager = getFragmentManager();
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setListAdapter(adapter);
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.listContainer, contactsFragment);
        View editContainer = findViewById(R.id.editContainer);
        if(editContainer != null) {
            EditFragment editFragment = new EditFragment();
            ft.add(R.id.editContainer, editFragment);
        }
        ft.commit();
    }

    private void initContacts() {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Fullard", "Minnaar", "mf@gmail.com", "0834877451"));
        contacts.add(new Contact("Potgieter", "Reta", "rpotg@gmail.com", "0714561789"));
    }

    @Override
    public void onContactSelected(int position) {
        addEditFragment().setPosition(position);
    }

    @Override
    public void onContactsSortClick(Comparator<Contact> comparator) {
        adapter.sort(comparator);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onContactAddClick() {
        addEditFragment();
    }

    private EditFragment addEditFragment() {
        EditFragment fragment;
        View editContainer = findViewById(R.id.editContainer);
        if(editContainer != null) { // in landscape view
            fragment = (EditFragment) getFragmentManager().findFragmentById(R.id.editContainer);
        } else { // portrait view
            fragment = new EditFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
        return fragment;
    }

    @Override
    public Contact getContact(int position) {
        return adapter.getItem(position);
    }

    @Override
    public void onApplyClick(int position, Contact contact) {
        if(position != -1)
            adapter.remove(adapter.getItem(position));
        adapter.add(contact);
        adapter.notifyDataSetChanged();
        getFragmentManager().popBackStackImmediate();
    }
}
