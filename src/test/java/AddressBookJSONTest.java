import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressBookJSONTest {

    @Test
    public void testGetAddressBook(){
        Response response = RestAssured.get("http://localhost:4000/addressbook");
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToRetrieveAllAddressBookData() {
        Response response = RestAssured.get("http://localhost:4000/addressbook/list");
        System.out.println(response.asString());
        Assertions.assertEquals(200, response.getStatusCode());
    }
}
