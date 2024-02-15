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
                System.out.println("Выберите операцию:");
                System.out.println("1. Создать запись");
                System.out.println("2. Прочитать запись");
                System.out.println("3. Обновить запись");
                System.out.println("4. Удалить запись");
                System.out.println("5. Выйти");
                System.out.print("Ваш выбор: ");
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
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Введите данные для создания записи:");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO my_table (name, age) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.executeUpdate();
            System.out.println("Запись успешно создана.");
        }
    }

    private static void readRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID записи для чтения: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "SELECT * FROM my_table WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    System.out.println("ID: " + id + ", Имя: " + name + ", Возраст: " + age);
                } else {
                    System.out.println("Запись с указанным ID не найдена.");
                }
            }
        }
    }

    private static void updateRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID записи для обновления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите новые данные:");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        String sql = "UPDATE my_table SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setInt(3, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Запись с указанным ID не найдена.");
            }
        }
    }

    private static void deleteRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Введите ID записи для удаления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM my_table WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Запись успешно удалена.");
            } else {
                System.out.println("Запись с указанным ID не найдена.");
            }
        }
    }
}
