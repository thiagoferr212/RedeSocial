package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen extends JFrame {
    public MainMenuScreen() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        
        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton friendsButton = new JButton("Amigos");
        friendsButton.setBounds(10, 20, 120, 25);
        friendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new FriendsScreen();
            }
        });
        panel.add(friendsButton);

        JButton chatButton = new JButton("Chat");
        chatButton.setBounds(10, 50, 120, 25);
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new ChatScreen();
            }
        });
        panel.add(chatButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(10, 80, 120, 25);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a tela do menu principal
                new LoginScreen(); // Volta para a tela de login
            }
        });
        panel.add(logoutButton);
        
    }
}

