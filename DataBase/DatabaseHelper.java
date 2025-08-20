package DataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:DataBase/contacts.db";

    public DatabaseHelper() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id TEXT PRIMARY KEY," +
                "firstName TEXT NOT NULL," +
                "surname TEXT NOT NULL," +
                "mobileNumber TEXT NOT NULL," +
                "address TEXT," +
                "email TEXT," +
                "dob TEXT," +  // store LocalDate as string
                "photoPath TEXT" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertPerson(Person p) {
        String sql = "INSERT INTO contacts (id, firstName, surname, mobileNumber, address, email, dob, photoPath) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getId().toString());
            pstmt.setString(2, p.getFirstName());
            pstmt.setString(3, p.getSurname());
            pstmt.setString(4, p.getMobileNumber());
            pstmt.setString(5, p.getAddress());
            pstmt.setString(6, p.getEmailAddress());
            pstmt.setString(7, p.getDateOfBirth() != null ? p.getDateOfBirth().toString() : null);
            pstmt.setString(8, p.getPathToProfilePhoto());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting person: " + e.getMessage());
        }
    }

    public List<Person> getAllPersons() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM contacts";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                try {
                    String id = rs.getString("id");
                    String firstName = rs.getString("firstName");
                    String surname = rs.getString("surname");
                    String mobile = rs.getString("mobileNumber");
                    String address = rs.getString("address");
                    String email = rs.getString("email");
                    String dobStr = rs.getString("dob");
                    String photoPath = rs.getString("photoPath");

                    LocalDate dob = dobStr != null ? LocalDate.parse(dobStr) : null;

                    Person p = new Person(firstName, surname, mobile, address,
                            dob != null ? dob.getDayOfMonth() : null,
                            dob != null ? dob.getMonth() : null,
                            dob != null ? dob.getYear() : null,
                            email, photoPath);

                    people.add(p);
                } catch (Exception e) {
                    System.out.println("Skipping record due to error: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching persons: " + e.getMessage());
        }

        return people;
    }

    public void deletePerson(String personId) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, personId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting person: " + e.getMessage());
        }
    }
}
