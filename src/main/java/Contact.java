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
}