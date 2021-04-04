import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
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

    @Test
    public void testToContactDataInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"firstName\": \"Anisha\",\"lastName\": \"Meshram\",\"address\": \"Hudkeshwar\",\"city\": \"Nagpur\",\"state\": \"Maharashtra\",\"zip\": \"440034\",\"phoneNumber\": \"8877445588\",\"email\": \"anisha@gmail.com\",\"personType\": \"Friend\"}")
                .when().post("http://localhost:4000/addressbook/create");
        response.then()
                .body("firstName", Matchers.is("Anisha"));
        Assertions.assertEquals(201, response.getStatusCode());
    }
}
