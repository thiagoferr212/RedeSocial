package RedeSocialProjeto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 20, 165, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (isEmpty(email) || isEmpty(password)) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                } else {
                    if (Authentication.authenticateUser(email, password)) {
                        // Usuário autenticado, navegar para a próxima tela
                        dispose(); // Fecha a tela de login
                        new MainMenuScreen();
                    } else {
                        // Exibir mensagem de erro de autenticação
                        JOptionPane.showMessageDialog(null, "Autenticação falhou. Verifique suas credenciais.");
                    }
                }
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Cadastrar");
        registerButton.setBounds(180, 80, 100, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chama o método para lidar com o registro
            	new RegistrationScreen();
            }
        });
        panel.add(registerButton);
    }
    private boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }
}