package RedeSocialProjeto;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationScreen extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField phoneNumberField;

    public RegistrationScreen() {
        setTitle("Cadastro");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
        
        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 50, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(100, 50, 165, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 80, 165, 25);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirmar Senha:");
        confirmPasswordLabel.setBounds(10, 110, 120, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(130, 110, 135, 25);
        panel.add(confirmPasswordField);

        JLabel phoneNumberLabel = new JLabel("Número de Telefone:");
        phoneNumberLabel.setBounds(10, 140, 150, 25);
        panel.add(phoneNumberLabel);

        phoneNumberField = new JTextField(20);
        phoneNumberField.setBounds(160, 140, 105, 25);
        panel.add(phoneNumberField);

        JButton registerButton = new JButton("Cadastrar");
        registerButton.setBounds(10, 170, 120, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        panel.add(registerButton);

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBounds(140, 170, 120, 25);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a tela de cadastro
            }
        });
        panel.add(cancelButton);
    }

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String phoneNumber = phoneNumberField.getText();
        
        if (isEmpty(name) || isEmpty(email) || isEmpty(password) || isEmpty(confirmPassword) || isEmpty(phoneNumber)) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
        } else {
            if (password.equals(confirmPassword)) {
                User user = new User(name, email, password, phoneNumber);
                user.saveToDatabase();
                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                dispose(); // Fecha a tela de cadastro
            } else {
                JOptionPane.showMessageDialog(null, "As senhas não coincidem. Tente novamente.");
            }
        }
    }
    private boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }
 }
