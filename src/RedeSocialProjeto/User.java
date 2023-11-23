package RedeSocialProjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class User {
		public boolean isResultSet;
    private int id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    // Construtor e getters/setters omitidos para brevidade

    public User() {
		// TODO Auto-generated constructor stub
	}
    
	public User(int id, String name2, String email2, String password2, String phoneNumber2) {
		this.id = id;
		this.name = name2;
		this.email = email2;
		this.password = password2;
		this.phoneNumber = phoneNumber2;
	}

	public User(String name2, String email2, String password2, String phoneNumber2) {
		this.name = name2;
		this.email = email2;
		this.password = password2;
		this.phoneNumber = phoneNumber2;
	}

	public User(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			this.isResultSet = true;
			this.id = resultSet.getInt("id");
			this.name = resultSet.getString("name");
			this.email = resultSet.getString("email");
			this.password = resultSet.getString("password");
			this.phoneNumber = resultSet.getString("phone_number");
		}
	}

	// Método para salvar o usuário no banco de dados
    public void saveToDatabase() {
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "INSERT INTO users (name, email, password, phone_number) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, phoneNumber);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
          if (e.getMessage().contains("duplicate key")) {
						JOptionPane.showMessageDialog(null, "O Usuário já está cadastrado!");
					}
        }
    }


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
