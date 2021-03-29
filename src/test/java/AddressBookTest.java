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
}
