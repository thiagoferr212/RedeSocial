package RedeSocialProjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friend {
    private int id;
    private int idUser;
    private int idFriend;

		public Friend() {}
		
		public Friend(int idUser, int idFriend) {
				this.idUser = idUser;
				this.idFriend = idFriend;
		}

		public void saveToDatabase() {
				try (Connection connection = DatabaseConnector.connect()) {
						String query = "INSERT INTO friends (user_id, id_amigo) VALUES (?, ?);";
						try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
								preparedStatement.setInt(1, idUser);
								preparedStatement.setInt(2, idFriend);

								preparedStatement.executeUpdate();
						}
				} catch (SQLException e) {
						e.printStackTrace();
				}
		}

		public void deleteToDatabase() {
			try (Connection connection = DatabaseConnector.connect()) {
					String query = "DELETE FROM friends WHERE user_id = ? AND id_amigo = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

					preparedStatement.setInt(1, idUser);
					preparedStatement.setInt(2, idFriend);
					preparedStatement.executeUpdate();
				} 
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
}
