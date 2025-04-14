import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Inicial {

    public static int WIDTH = 700;
    public static int HEIGHT = 700;
    public static int PWIDTH = 40;
    public static int PHEIGHT = 40;
    public static int PSPEED = 10;
    public static int PX = 300;
    public static int PY = 300;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Java survivors");
        JPanel painel = new GamePanel();

        painel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(painel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public static class GamePanel extends JPanel implements Runnable {

        private Thread tred;
        private boolean isRunning;
        private Set<Integer> teclasPressionadas = new HashSet<>();

        private int playerX, playerY, pWidth = PWIDTH, pHeight = PHEIGHT;

        public GamePanel() {
            setFocusable(true);
            SwingUtilities.invokeLater(() -> requestFocusInWindow());

            requestFocusInWindow();
            addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    teclasPressionadas.add(e.getKeyCode());
                }

                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    teclasPressionadas.remove(e.getKeyCode());
                }
            });

            startGame();
        }

        public void startGame() {
            playerX = PX;
            playerY = PY;

            isRunning = true;
            tred = new Thread(this);
            tred.start();
        }

        @Override
        public void run() {
            while (isRunning) {
                update();
                repaint();

                try {
                    Thread.sleep(16); // ~60 FPS (1000ms / 60)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void update() {
            double dx = 0, dy = 0;

            // Esquerda / A 
            if (teclasPressionadas.contains(KeyEvent.VK_LEFT) || teclasPressionadas.contains(KeyEvent.VK_A)) {
                dx -= 1;
            }
            // Direita / D
            if (teclasPressionadas.contains(KeyEvent.VK_RIGHT) || teclasPressionadas.contains(KeyEvent.VK_D)) {
                dx += 1;
            }
            // Cima / W
            if (teclasPressionadas.contains(KeyEvent.VK_UP) || teclasPressionadas.contains(KeyEvent.VK_W)) {
                dy -= 1;
            }
            // Baixo / S
            if (teclasPressionadas.contains(KeyEvent.VK_DOWN) || teclasPressionadas.contains(KeyEvent.VK_S)) {
                dy += 1;
            }

            // Normalizar o vetor (diagonal = 1)
            double comprimento = Math.sqrt(dx * dx + dy * dy);
            if (comprimento != 0) {
                dx /= comprimento;
                dy /= comprimento;
            }

            // Transforma o double em int
            int nextX = (int) (playerX + dx * PSPEED);
            int nextY = (int) (playerY + dy * PSPEED);

            if (nextX >= 0 && nextX + pWidth <= getWidth()) {
                playerX = nextX;
            }

            if (nextY >= 0 && nextY + pHeight <= getHeight()) {
                playerY = nextY;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Fundo
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Player
            g.setColor(Color.BLACK);
            g.fillRect(playerX, playerY, pWidth, pHeight);
        }

    }
}
