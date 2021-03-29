import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;

    private AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    public List<Contact> readData() {
        String sql = "SELECT * from addressbook;";
        return this.getContactDetailsUsingSqlQuery(sql);
    }

    private List<Contact> getContactDetailsUsingSqlQuery(String sql) {
        List<Contact> ContactList = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery(sql);
            ContactList = this.getAddressBookData(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ContactList;
    }

    private List<Contact> getAddressBookData(ResultSet resultSet) {
        List<Contact> contactList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                int zip = resultSet.getInt("zip");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");
                String type = resultSet.getString("type");
                contactList.add(new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email,
                        type));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/address_book_jdbc?useSSL=false";
        String userName = "root";
        String password = "Ani1997@";
        Connection connection;
        System.out.println("Connecting to database: " + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection successful: " + connection);
        return connection;
    }
}