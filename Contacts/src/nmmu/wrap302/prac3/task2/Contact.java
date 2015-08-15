package nmmu.wrap302.prac3.task2;

/**
 * @author minnaar
 * @since 2015/08/06.
 */
public class Contact {
    public String surname;
    public String name;
    public String email;
    public String cell;

    public Contact() {
        this.surname = "";
        this.name = "";
        this.email = "";
        this.cell = "";
    }

    public Contact(String surname, String name, String email, String cell) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.cell = cell;
    }

    @Override
    public String toString() {
        return String.format(
                "%s, %s, %s, %s",
                surname,
                name,
                email,
                cell
        );
    }
}
