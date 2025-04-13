import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        frame.setVisible(true);

    }

    public static class GamePanel extends JPanel implements Runnable {

        private Thread tred;
        private boolean isRunning;

        private int playerX, playerY, pWidth = PWIDTH, pHeight = PHEIGHT;

        public GamePanel() {
            setFocusable(true);
            SwingUtilities.invokeLater(() -> requestFocusInWindow());

            requestFocusInWindow();
            addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    mover(e);
                }
            });
            startGame();
        }

        public void mover(java.awt.event.KeyEvent e) {
            int tecla = e.getKeyCode();

            if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
                playerX -= PSPEED;
            }
            if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
                playerX += PSPEED;
            }
            if (tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) {
                playerY -= PSPEED;
            }
            if (tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) {
                playerY += PSPEED;
            }
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
