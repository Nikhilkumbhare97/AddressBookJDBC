import java.util.List;

public class AddressBookService {
    private final AddressBookDBService addressBookDBService;

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public List<Contact> readContactData() {
        return addressBookDBService.readData();
    }
}
