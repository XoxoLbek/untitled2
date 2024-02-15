import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseCRUD {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Dias";
    private static final String USER = "postgres";
    private static final String PASSWORD = "NURBANU2019";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("Choose operation:");
                System.out.println("1. Create record");
                System.out.println("2. Read record");
                System.out.println("3. Update record");
                System.out.println("4. Delete record");
                System.out.println("5. Exit");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createRecord(connection, scanner);
                        break;
                    case 2:
                        readRecord(connection, scanner);
                        break;
                    case 3:
                        updateRecord(connection, scanner);
                        break;
                    case 4:
                        deleteRecord(connection, scanner);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Enter data to create record:");
        System.out.print("Apartment number: ");
        int apartmentNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        String sql = "INSERT INTO my_table (apartment_number, name, age, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, apartmentNumber);
            statement.setString(2, name);
            statement.setInt(3, age);
            statement.setString(4, phoneNumber);
            statement.executeUpdate();
            System.out.println("Record created successfully.");
        }
    }

    private static void readRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter apartment number to read: ");
        int apartmentNumber = scanner.nextInt();
        scanner.nextLine();

        String sql = "SELECT * FROM my_table WHERE apartment_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, apartmentNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String phoneNumber = resultSet.getString("phone_number");
                    System.out.println("ID: " + id + ", Apartment number: " + apartmentNumber + ", Name: " + name + ", Age: " + age + ", Phone number: " + phoneNumber);
                } else {
                    System.out.println("Records not found for the specified apartment.");
                }
            }
        }
    }

    private static void updateRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter apartment number to update: ");
        int apartmentNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter new data:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        String sql = "UPDATE my_table SET name = ?, age = ?, phone_number = ? WHERE apartment_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, phoneNumber);
            statement.setInt(4, apartmentNumber);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("Records not found for the specified apartment.");
            }
        }
    }

    private static void deleteRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter apartment number to delete: ");
        int apartmentNumber = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM my_table WHERE apartment_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, apartmentNumber);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Records deleted successfully.");
            } else {
                System.out.println("Records not found for the specified apartment.");
            }
        }
    }
}
