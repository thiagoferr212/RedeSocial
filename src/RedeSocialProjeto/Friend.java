package RedeSocialProjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friend {
    private String id;
    private String idUser;
    private String idFriend;

		public Friend() {}
		
		public Friend(String idUser, String idFriend) {
				this.idUser = idUser;
				this.idFriend = idFriend;
		}

		public void saveToDatabase() {
				try (Connection connection = DatabaseConnector.connect()) {
						String query = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?), (?, ?);";
						try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
								preparedStatement.setString(1, idUser);
								preparedStatement.setString(2, idFriend);
								preparedStatement.setString(3, idFriend);
								preparedStatement.setString(4, idUser);

								preparedStatement.executeUpdate();
						}
				} catch (SQLException e) {
						e.printStackTrace();
				}
		}

		public void deleteToDatabase() {
			try (Connection connection = DatabaseConnector.connect()) {
					String query = "DELETE FROM friends " +
					"WHERE user_id = ? AND friend_id = (SELECT id FROM users WHERE name = ?)";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

					preparedStatement.setString(1, idUser);
					preparedStatement.setString(2, idFriend);
					preparedStatement.setString(3, idFriend);
					preparedStatement.setString(4, idUser);
					preparedStatement.executeUpdate();
				} 
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}

		public String getFriendName() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setFriendName(String string) {
			// TODO Auto-generated method stub
			
		}
}
