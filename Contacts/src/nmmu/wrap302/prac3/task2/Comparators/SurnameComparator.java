package nmmu.wrap302.prac3.task2.Comparators;

import nmmu.wrap302.prac3.task2.Contact;

import java.util.Comparator;

/**
 * @author minnaar
 * @since 2015/08/09.
 */
public class SurnameComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact lhs, Contact rhs) {
        return lhs.surname.compareTo(rhs.surname);
    }
}
