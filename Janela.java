import javax.swing.*;
import java.awt.*;
import static utils.Config.*;

public class Janela {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java Wizard");
        JPanel painel = new GamePanel();

        painel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(painel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
