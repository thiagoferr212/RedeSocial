package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatScreen extends JFrame {
    public ChatScreen() {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton sendMessageButton = new JButton("Enviar Mensagem");
        sendMessageButton.setBounds(10, 20, 150, 25);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(sendMessageButton);
    }

    private void sendMessage() {
        // Implemente a lógica para enviar uma mensagem
        JOptionPane.showMessageDialog(null, "Lógica para enviar mensagem a ser implementada.");
    }
}
