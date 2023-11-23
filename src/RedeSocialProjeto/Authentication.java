package RedeSocialProjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    public static User usuarioAtual;

    public static boolean authenticateUser(String email, String password) {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    usuarioAtual = new User(resultSet);
                    return usuarioAtual.isResultSet;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

