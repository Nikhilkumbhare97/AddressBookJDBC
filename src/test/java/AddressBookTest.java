import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddressBookTest {
    @Test
    public void contactsWhenRetrievedFromDB_ShouldMatchCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readContactData();
        Assertions.assertEquals(5, contactList.size());
    }

    @Test
    public void givenNewAddressForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readContactData();
        addressBookService.updateContactDetails("Nikhil", "Jamnagar");
        boolean result = addressBookService.checkContactInSyncWithDB("Nikhil");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readContactData();
        LocalDate startDate = LocalDate.of(2019, 11, 1);
        LocalDate endDate = LocalDate.now();
        List<Contact> contactList = addressBookService.readContactDataForGivenDateRange(startDate, endDate);
        Assertions.assertEquals(4, contactList.size());
    }

    @Test
    public void givenContacts_RetrieveNumberOfContacts_ByCityOrState() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readContactData();
        Map<String, Integer> contactByCityOrStateMap = addressBookService.readContactByCityOrState();
        Assertions.assertEquals((int) contactByCityOrStateMap.get("Pune"), 2);
        Assertions.assertEquals((int) contactByCityOrStateMap.get("Maharashtra"), 5);
    }

    @Test
    public void givenNewContact_WhenAdded_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readContactData();
        addressBookService.addContactToDatabase("Rishi", "Badhe", "ShivajiNagar", "Shegaon", "Maharashtra", 425894,
                "9875896425", "rishi@gmail.com", "Friend", Date.valueOf("2021-03-21"));
        boolean result = addressBookService.checkContactInSyncWithDB("Rishi");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenContacts_WhenAddedToDB_ShouldMatchEmployeeEntries() {
        Contact[] arrayOfEmployee = {
                new Contact("Rajesh", "Dharmik", "ModiNo6", "Nagpur", "Maharashtra", 440016, "9865430031",
                        "rajeshd@gmail.com", "Profession", Date.valueOf("2021-03-25")),
                new Contact("Shreya", "Ghosh", "PimpleGurav", "Pune", "Maharashtra", 411055, "9865854331",
                        "ghoshalshreya@gmail.com", "Friend", Date.valueOf("2021-03-28"))};
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readData(AddressBookService.IOService.DB_IO);
        Instant start = Instant.now();
        addressBookService.addContact(Arrays.asList(arrayOfEmployee));
        Instant end = Instant.now();
        System.out.println("Duration without thread : " + Duration.between(start, end));
        Instant threadStart = Instant.now();
        addressBookService.addEmployeeToPayrollWithThreads(Arrays.asList(arrayOfEmployee));
        Instant threadEnd = Instant.now();
        System.out.println("Duartion with Thread : " + Duration.between(threadStart, threadEnd));
        Assertions.assertEquals(7, addressBookService.countEntries());
    }
}
