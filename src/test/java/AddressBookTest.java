import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AddressBookTest {
    @Test
    public void contactsWhenRetrievedFromDB_ShouldMatchCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readContactData();
        Assertions.assertEquals(4, contactList.size());
    }

    @Test
    public void givenNewAddressForPerson_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readContactData();
        addressBookService.updateContactDetails("Nikhil", "Jamnagar");
        boolean result = addressBookService.checkContactInSyncWithDB("Nikhil");
        Assertions.assertTrue(result);
    }
}
