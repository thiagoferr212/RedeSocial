package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class FriendsScreen extends JFrame {
	private DefaultListModel<String> userListModel;
	private JList<String> userList;

	public FriendsScreen() {
        setTitle("Amigos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

//    private void placeComponents(JPanel panel) {
//        panel.setLayout(null);
//
//        JButton includeButton = new JButton("Incluir Amigo");
//        includeButton.setBounds(10, 20, 150, 25);
//        includeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                includeFriend();
//            }
//        });
//        panel.add(includeButton);
//
//        JButton consultButton = new JButton("Consultar Amigos");
//        consultButton.setBounds(10, 50, 150, 25);
//        consultButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                consultFriends();
//            }
//        });
//        panel.add(consultButton);
//
//        JButton excludeButton = new JButton("Excluir Amigo");
//        excludeButton.setBounds(10, 80, 150, 25);
//        excludeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                excludeFriend();
//            }
//        });
//        panel.add(excludeButton);
//    }
	
	private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        updateUsersList(); // Atualiza a lista de usuários ao iniciar
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(10, 20, 150, 150);
        panel.add(scrollPane);

        JButton includeButton = new JButton("Incluir Amigo");
        includeButton.setBounds(10, 180, 150, 25);
        includeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                includeFriend();
            }
        });
        panel.add(includeButton);
    }
    
    private void updateUsersList() {
        // Busca todos os usuários no banco de dados e atualiza a lista
        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT name FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String userName = resultSet.getString("name");
                        userListModel.addElement(userName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void includeFriend() {
    	String selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            // Implemente a lógica para incluir o amigo selecionado
            JOptionPane.showMessageDialog(null, "Amigo incluído: " + selectedUser);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuário antes de incluir como amigo.");
        }
    }

    private void consultFriends() {
        // Implemente a lógica para consultar amigos
        JOptionPane.showMessageDialog(null, "Lógica para consultar amigos a ser implementada.");
    }

    private void excludeFriend() {
        // Implemente a lógica para excluir um amigo
        JOptionPane.showMessageDialog(null, "Lógica para excluir amigo a ser implementada.");
    }
}
