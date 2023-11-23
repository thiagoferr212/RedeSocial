package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatScreen extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> friendComboBox;

    private List<Friend> friendsList;  // Lista de objetos amigos

    public ChatScreen() {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        friendsList = getFriendsFromDatabase();  // Obtém a lista de amigos do banco de dados

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private List<Friend> getFriendsFromDatabase() {
        List<Friend> friends = new ArrayList<>();

        try (Connection connection = DatabaseConnector.connect()) {
            // Consulta SQL para obter a lista de amigos do banco de dados
            String query = "SELECT f.id_amigo FROM friends f WHERE f.user_id = ?";
            
            // Supondo que você tenha uma função real que obtenha o ID do usuário atual
            int userId = getUserId();

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Cria um objeto Friend para cada amigo
                        Friend friend = new Friend();
                        friend.setFriendName(resultSet.getString("friend_name"));
                        friends.add(friend);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    private int getUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(10, 10, 350, 150);
        panel.add(scrollPane);

        friendComboBox = new JComboBox<>();
        for (Friend friend : friendsList) {
            friendComboBox.addItem(friend.getFriendName());
        }
        friendComboBox.setBounds(10, 170, 150, 25);
        panel.add(friendComboBox);

        messageField = new JTextField();
        messageField.setBounds(10, 200, 250, 25);
        panel.add(messageField);

        JButton sendMessageButton = new JButton("Enviar Mensagem");
        sendMessageButton.setBounds(270, 200, 120, 25);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(sendMessageButton);
    }

    private void sendMessage() {
        String messageText = messageField.getText().trim();

        if (!messageText.isEmpty()) {
            String selectedFriendName = (String) friendComboBox.getSelectedItem();

            // Verifica se um amigo foi selecionado
            if (selectedFriendName != null) {
                // Obtém a data e hora atual
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                // Monta a mensagem com a data, hora, remetente e conteúdo
                String message = "[" + formattedDateTime + "] Você para " + selectedFriendName + ": " + messageText + "\n";

                // Adiciona a mensagem à área de chat
                chatArea.append(message);

                // Limpa o campo de texto
                messageField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um amigo para enviar a mensagem.");
            }
        }
    }
}
