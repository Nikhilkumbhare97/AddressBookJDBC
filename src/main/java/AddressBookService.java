import java.time.LocalDate;
import java.util.List;

public class AddressBookService {
    private final AddressBookDBService addressBookDBService;
    public List<Contact> contactList;

    public AddressBookService() {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    @SuppressWarnings("unused")
    public AddressBookService(List<Contact> contactList, AddressBookDBService addressBookDBService) {
        this.contactList = contactList;
        this.addressBookDBService = addressBookDBService;
    }

    public List<Contact> readContactData() {
        this.contactList = addressBookDBService.readData();
        return contactList;
    }

    public void updateContactDetails(String name, String address) {
        int result = addressBookDBService.updateEmployeeData(name, address);
        if (result == 0)
            return;
        Contact personInfo = this.getContactData(name);
        if (personInfo != null)
            personInfo.address = address;
    }

    private Contact getContactData(String firstName) {
        return this.contactList.stream().filter(contact -> contact.firstName.equals(firstName)).findFirst().orElse(null);
    }

    public boolean checkContactInSyncWithDB(String firstName) {
        List<Contact> contactList = addressBookDBService.getContactDataByName(firstName);
        return contactList.get(0).equals(getContactData(firstName));
    }

    public List<Contact> readContactDataForGivenDateRange(LocalDate startDate, LocalDate endDate) {
        this.contactList = addressBookDBService.getContactForGivenDateRange(startDate, endDate);
        return contactList;
    }
}
