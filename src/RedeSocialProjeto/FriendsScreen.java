package RedeSocialProjeto;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsScreen extends JFrame {
    private List<User> allUsers; // Lista fictícia de todos os usuários cadastrados
    private List<User> friendsList; // Lista de amigos do usuário atual

    public FriendsScreen() {
        setTitle("Amigos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        allUsers = getAllUsers(); // Simulação: obter todos os usuários do banco de dados
        friendsList = getFriendsList(); // Simulação: obter amigos do usuário atual do banco de dados

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        
        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private List<User> getAllUsers() {
        // Simulação: obter todos os usuários do banco de dados
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT * FROM users WHERE name != ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, Authentication.usuarioAtual.getName());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        users.add(new User(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<User> getFriendsList() {
        // Simulação: obter amigos do usuário atual do banco de dados
        List<User> friends = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect()) {
            int userId = getUserId(); // Replace with a real function to get the user ID
            String query = "SELECT u.id, u.name FROM friends f " +
                           "JOIN users u ON f.id_amigo = u.id " +
                           "WHERE f.user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        friends.add(new User(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton includeButton = new JButton("Incluir Amigo");
        includeButton.setBounds(10, 20, 150, 25);
        includeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                includeFriend();
            }
        });
        panel.add(includeButton);

        JButton consultButton = new JButton("Consultar Amigos");
        consultButton.setBounds(10, 50, 150, 25);
        consultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultFriends();
            }
        });
        panel.add(consultButton);

        JButton excludeButton = new JButton("Excluir Amigo");
        excludeButton.setBounds(10, 80, 150, 25);
        excludeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excludeFriend();
            }
        });
        panel.add(excludeButton);
    }

    private void includeFriend() {
        // Simulação: exibir uma lista de usuários e permitir a inclusão de amigos
        int i = 0;
        String[] usersArray = new String[allUsers.size()];
        for (User user : allUsers) {
            usersArray[i] = user.getName();
        }

        String selectedUser = (String) JOptionPane.showInputDialog(
                null,
                "Escolha um usuário para adicionar como amigo:",
                "Incluir Amigo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                usersArray,
                usersArray[0]
        );
        
        User userFriend = new User();
        for (User user : allUsers) {
            if (user.getName() == selectedUser) {
                userFriend = user;               
            }
        }
        
        if (selectedUser != null && !friendsList.contains(userFriend)) {
            friendsList.add(userFriend);
            updateFriendsList(userFriend, true); // Inclui amigo no banco de dados
            JOptionPane.showMessageDialog(null, selectedUser + " foi adicionado como amigo.");
        } else if (friendsList.contains(userFriend)) {
            JOptionPane.showMessageDialog(null, selectedUser + " já é seu amigo.");
        }
    }

    private void updateFriendsList(User userFriend, boolean addFriend) {
        // Atualiza a lista de amigos no banco de dados
        try (Connection connection = DatabaseConnector.connect()) {
            Friend amigo = new Friend(getUserId(), userFriend.getId());

            if (addFriend) {
                amigo.saveToDatabase();
            } else {
                amigo.deleteToDatabase();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserId() {
		return Authentication.usuarioAtual.getId();
	}

    private void consultFriends() {
        // Obtém a lista de amigos do banco de dados
        List<User> friendsFromDatabase = getFriendsFromDatabase();
        
        if (friendsFromDatabase.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você não tem amigos.");
        } else {
            // Exibe a lista de amigos
            StringBuilder message = new StringBuilder("Seus amigos:\n");
            for (User friend : friendsFromDatabase) {
                message.append("- ").append(friend.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }

    private List<User> getFriendsFromDatabase() {
        List<User> friends = new ArrayList<>();
        
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT u.* FROM users u INNER JOIN friends f ON u.id = f.id_amigo WHERE f.user_id = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, getUserId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        friends.add(new User(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

	private void excludeFriend() {
        // Simulação: exibir a lista de amigos e permitir a exclusão de amigos

        if (friendsList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você não tem amigos para excluir.");
            return;
        }

        int i = 0;
        String[] friendsArray = new String[friendsList.size()];
        for (User user : friendsList) {
            friendsArray[i] = user.getName();
        }

        String selectedFriend = (String) JOptionPane.showInputDialog(
                null,
                "Escolha um amigo para excluir:",
                "Excluir Amigo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                friendsArray,
                friendsArray[0]
        );

        User userFriend = new User();
        for (User user : friendsList) {
            if (user.getName() == selectedFriend) {
                userFriend = user;            
            }
        }

        if (selectedFriend != null) {
            friendsList.remove(userFriend);
            updateFriendsList(userFriend, false); // Atualiza a lista de amigos no banco de dados
            JOptionPane.showMessageDialog(null, selectedFriend + " foi removido da sua lista de amigos.");
        }
    }
}
       