package RedeSocialProjeto;
import javax.swing.*;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginScreen();
        });
    }
}