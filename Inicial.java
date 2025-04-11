import javax.swing.*;
import java.awt.*;

public class Inicial {

    public static int WIDTH = 700;
    public static int HEIGHT = 700;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Java survivors");
        JPanel painel = new GamePanel();

        painel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(painel);
        frame.pack();
        frame.setVisible(true);

    }

    public static class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int tamanho = 50;
            int cordX = (getWidth() - tamanho) / 2;
            int cordY = (getHeight() - tamanho) / 2;

            g.setColor(Color.BLACK);
            g.fillRect(cordX, cordY, tamanho, tamanho);
        }
    }
}
