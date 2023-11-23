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
    private List<String> allUsers; // Lista fictícia de todos os usuários cadastrados
    private List<String> friendsList; // Lista de amigos do usuário atual

    public FriendsScreen() {
        setTitle("Amigos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        allUsers = getAllUsers(); // Simulação: obter todos os usuários do banco de dados
        friendsList = getFriendsList(); // Simulação: obter amigos do usuário atual do banco de dados

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private List<String> getAllUsers() {
        // Simulação: obter todos os usuários do banco de dados
        List<String> users = new ArrayList<>();
        users.add("Usuario1");
        users.add("Usuario2");
        users.add("Usuario3");
        // Adicione mais usuários conforme necessário
        return users;
    }

    private List<String> getFriendsList() {
        // Simulação: obter amigos do usuário atual do banco de dados
        List<String> friends = new ArrayList<>();
        friends.add("Amigo1");
        friends.add("Amigo2");
        // Adicione mais amigos conforme necessário
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
        String[] usersArray = allUsers.toArray(new String[0]);
        String selectedUser = (String) JOptionPane.showInputDialog(
                null,
                "Escolha um usuário para adicionar como amigo:",
                "Incluir Amigo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                usersArray,
                usersArray[0]
        );

        if (selectedUser != null && !friendsList.contains(selectedUser)) {
            friendsList.add(selectedUser);
            updateFriendsList1(); // Inclui amigo no banco de dados
            JOptionPane.showMessageDialog(null, selectedUser + " foi adicionado como amigo.");
        } else if (friendsList.contains(selectedUser)) {
            JOptionPane.showMessageDialog(null, selectedUser + " já é seu amigo.");
        }
    }

    private void updateFriendsList1() {
        // Simulação: Atualizar a lista de amigos no banco de dados
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "INSERT INTO friends (user_id, friend_name) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Simulação: Supondo que você tenha uma tabela 'friends' com colunas 'user_id' e 'friend_name'
                int userId = getUserId(); // Substitua por uma função real que obtenha o ID do usuário atual
                preparedStatement.setInt(1, userId);
                String selectedUser = null;
				preparedStatement.setString(2, selectedUser);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

    private void consultFriends() {
        // Obtém a lista de amigos do banco de dados
        List<String> friendsFromDatabase = getFriendsFromDatabase();
        
        if (friendsFromDatabase.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você não tem amigos.");
        } else {
            // Exibe a lista de amigos
            StringBuilder message = new StringBuilder("Seus amigos:\n");
            for (String friend : friendsFromDatabase) {
                message.append("- ").append(friend).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }

    private List<String> getFriendsFromDatabase() {
        List<String> friends = new ArrayList<>();
        
        try (Connection connection = DatabaseConnector.connect()) {
            // Simulação: Supondo que você tenha uma tabela 'users' com colunas 'id' e 'name', 
            // e uma tabela 'friends' com colunas 'user_id' e 'friend_id' (ou 'friend_name')
            String query = "SELECT u.name FROM users u INNER JOIN friends f ON u.id = f.friend_id WHERE f.user_id = ?";
            
            // Supondo que você tenha uma função real que obtenha o ID do usuário atual
            int userId = getUserId();
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        friends.add(resultSet.getString("name"));
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

        String[] friendsArray = friendsList.toArray(new String[0]);
        String selectedFriend = (String) JOptionPane.showInputDialog(
                null,
                "Escolha um amigo para excluir:",
                "Excluir Amigo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                friendsArray,
                friendsArray[0]
        );

        if (selectedFriend != null) {
            friendsList.remove(selectedFriend);
            updateFriendsList1(); // Simulação: Atualizar a lista de amigos no banco de dados
            JOptionPane.showMessageDialog(null, selectedFriend + " foi removido da sua lista de amigos.");
        }
    }
}
