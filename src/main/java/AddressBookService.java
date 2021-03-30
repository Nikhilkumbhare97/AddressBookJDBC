import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void readData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            this.contactList = addressBookDBService.readData();
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

    public Map<String, Integer> readContactByCityOrState() {
        return addressBookDBService.getContactsByCityOrState();
    }

    public long countEntries() {
        return contactList.size();
    }

    public void addContactToDatabase(String firstName, String lastName, String address, String city, String state,
                                     int zip, String phoneNumber, String email, String type, Date date) {
        contactList.add(addressBookDBService.addContact(firstName, lastName, address, city, state, zip, phoneNumber, email, type, (java.sql.Date) date));
    }

    public void addContact(List<Contact> contactDataList) {
        contactDataList.forEach(contactData -> {
            System.out.println("Employee being added : " + contactData.firstName);
            this.addContactToDatabase(contactData.firstName, contactData.lastName, contactData.address, contactData.city,
                    contactData.state, contactData.zip, contactData.phoneNumber, contactData.email,
                    contactData.type, contactData.date);
            System.out.println("Employee added : " + contactData.firstName);
        });
        System.out.println("" + this.contactList);
    }

    public void addEmployeeToPayrollWithThreads(List<Contact> contactDataList) {
        Map<Integer, Boolean> employeeAdditionStatus = new HashMap<>();
        contactDataList.forEach(contactData -> {
            Runnable task = () -> {
                employeeAdditionStatus.put(contactData.hashCode(), false);
                System.out.println("Employee being added : " + Thread.currentThread().getName());
                this.addContactToDatabase(contactData.firstName, contactData.lastName, contactData.address, contactData.city,
                        contactData.state, contactData.zip, contactData.phoneNumber, contactData.email,
                        contactData.type, contactData.date);
                employeeAdditionStatus.put(contactData.hashCode(), true);
                System.out.println("Employee added : " + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, contactData.firstName);
            thread.start();
        });
        while (employeeAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("" + this.contactList);
    }

    public enum IOService {
        DB_IO
    }
}
