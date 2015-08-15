package nmmu.wrap302.prac3.task2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import nmmu.wrap302.prac3.task2.Comparators.CellComparator;
import nmmu.wrap302.prac3.task2.Comparators.EmailComparator;
import nmmu.wrap302.prac3.task2.Comparators.SurnameComparator;

import java.util.Comparator;

/**
 * @author minnaar
 * @since 2015/08/14.
 */
public class ContactsFragment extends android.app.ListFragment {
    private OnContactSelectedListener selectListener;
    private OnContactsSortClickListener sortListener;
    private OnContactAddClickListener addListener;

    public interface OnContactSelectedListener {
        public void onContactSelected(int position);
    }

    public interface OnContactsSortClickListener {
        public void onContactsSortClick(Comparator<Contact> comparator);
    }

    public interface OnContactAddClickListener {
        public void onContactAddClick();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectListener = (OnContactSelectedListener) activity;
            sortListener = (OnContactsSortClickListener) activity;
            addListener = (OnContactAddClickListener) activity;
        } catch (ClassCastException e) {
            Log.e(getClass().getName(), String.format("%s needs to implement listeners", activity.toString()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.contact_list, null, false);
        Button btnSort = (Button) view.findViewById(R.id.btnSort);
        Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
                Comparator<Contact> comparator;
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radioCell:
                        comparator = new CellComparator();
                        break;
                    case R.id.radioEmail:
                        comparator = new EmailComparator();
                        break;
                    default:
                        comparator = new SurnameComparator();
                        break;
                }
                sortListener.onContactsSortClick(comparator);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListener.onContactAddClick();
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        selectListener.onContactSelected(position);
        getListView().setItemChecked(position, true);
    }
}