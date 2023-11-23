package RedeSocialProjeto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/RedeSocial";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void InicializeDB(){
        try (Connection connection = DatabaseConnector.connect()) {
            String commandMessages = "DROP TABLE IF EXISTS messages CASCADE ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(commandMessages)) {
                preparedStatement.executeQuery();
            }
            
            String commandFriends = "DROP TABLE IF EXISTS friends CASCADE ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(commandFriends)) {
                preparedStatement.executeQuery();
            }
            
            String commandUsers = "DROP TABLE IF EXISTS users CASCADE ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(commandUsers)) {
                preparedStatement.executeQuery();
            }
            String createUsers = "CREATE TABLE users (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    name VARCHAR(255) NOT NULL,\n" +
                    "    email VARCHAR(255) NOT NULL UNIQUE,\n" +
                    "    password VARCHAR(255) NOT NULL,\n" +
                    "    phone_number VARCHAR(20)\n" +
                    ");";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createUsers)) {
                preparedStatement.executeQuery();
            }
            String createFriends = "CREATE TABLE friends (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    user_id int NOT NULL,\n" +
                    "    id_amigo int NOT NULL\n" +
                    ");";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createFriends)) {
                preparedStatement.executeQuery();
            }
            String createMessages = "CREATE TABLE messages (\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    friend_id int NOT NULL,\n" +
                    "    message VARCHAR(255)\n" +
                    ");";
            try (PreparedStatement preparedStatement = connection.prepareStatement(createMessages)) {
                preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
