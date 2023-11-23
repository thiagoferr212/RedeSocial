package RedeSocialProjeto;
import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        DatabaseConnector.InicializeDB();
        SwingUtilities.invokeLater(() -> {
            new LoginScreen();
        });
    }
}