package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatScreen extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JComboBox<String> friendComboBox;

    private List<String> friendsList;  // Obtém a lista de amigos do banco de dados

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

    private List<String> getFriendsFromDatabase() {
        // Lógica para obter a lista de amigos do banco de dados
        // Substitua isso com a sua lógica real
        return List.of("Amigo1", "Amigo2", "Amigo3");
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(10, 10, 350, 150);
        panel.add(scrollPane);

        friendComboBox = new JComboBox<>(friendsList.toArray(new String[0]));
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
            String selectedFriend = (String) friendComboBox.getSelectedItem();

            // Verifica se um amigo foi selecionado
            if (selectedFriend != null) {
                // Obtém a data e hora atual
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                // Monta a mensagem com a data, hora, remetente e conteúdo
                String message = "[" + formattedDateTime + "] Você para " + selectedFriend + ": " + messageText + "\n";

                // Adiciona a mensagem à área de chat
                chatArea.append(message);

                // Limpa o campo de texto
                messageField.setText(message);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um amigo para enviar a mensagem.");
            }
        }
    }
}
