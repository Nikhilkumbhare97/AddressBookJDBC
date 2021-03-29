import java.util.Objects;

public class Contact {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String email;
    public String type;

    public Contact(String firstName, String lastName, String address, String city, String state, int zip,
                   String phoneNumber, String email, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return zip == contact.zip && Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName) && Objects.equals(address, contact.address) && Objects.equals(city, contact.city) && Objects.equals(state, contact.state) && Objects.equals(phoneNumber, contact.phoneNumber) && Objects.equals(email, contact.email) && Objects.equals(type, contact.type);
    }
}
