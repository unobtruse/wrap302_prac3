package nmmu.wrap302.prac3.task2;

import android.app.Activity;
import android.app.Fragment;
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
        FragmentTransaction ft = manager.beginTransaction();
        ContactsFragment contactsFragment = new ContactsFragment();
        if(manager.findFragmentById(R.id.listContainer) == null) {
            ft.add(R.id.listContainer, contactsFragment);
        } else {
            ft.replace(R.id.listContainer, contactsFragment);
        }
        View editContainer = findViewById(R.id.editContainer);
        if(editContainer != null) {
            EditFragment editFragment = (EditFragment) manager.findFragmentById(R.id.editContainer);
            if(editFragment == null) {
                editFragment = new EditFragment();
                ft.add(R.id.editContainer, editFragment);
            }
        }
        contactsFragment.setListAdapter(adapter);
        ft.commit();
    }

    private void initContacts() {
        contacts = new ArrayList<>();
        contacts.add(new Contact("Fullard", "Minnaar", "mf@gmail.com", "0834877451"));
        contacts.add(new Contact("Potgieter", "Reta", "rpotg@gmail.com", "0714561789"));
    }

    @Override
    public void onContactSelected(int position) {
        addEditFragment(false).setPosition(position);
    }

    @Override
    public void onContactsSortClick(Comparator<Contact> comparator) {
        adapter.sort(comparator);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onContactAddClick() {
        addEditFragment(true);
    }

    private EditFragment addEditFragment(boolean clear) {
        EditFragment fragment = new EditFragment();
        View editContainer = findViewById(R.id.editContainer);
        if(editContainer != null) { // in landscape view
            if(!clear) {
                fragment = (EditFragment) getFragmentManager().findFragmentById(R.id.editContainer);
                if(fragment == null)
                    fragment = new EditFragment();
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.editContainer, fragment)
                    .commit();
        } else { // portrait view
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
