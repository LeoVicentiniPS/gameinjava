import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Inicial {

    public static int WIDTH = 700;
    public static int HEIGHT = 700;
    public static int PWIDTH = 40;
    public static int PHEIGHT = 40;
    public static int PSPEED = 5;
    public static int PX = 300;
    public static int PY = 300;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Java Wizard");
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
        private int mouseX = WIDTH / 2;
        private int mouseY = HEIGHT / 2;
        private int playerX, playerY; 
        private int pWidth = PWIDTH;
        private int pHeight = PHEIGHT;
        private double pjCooldown = 1.5;
        private double pjTimer = 0;


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

            addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
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

            // Projetil 
            pjTimer += 1.0 / 60.0; // cada frame vale ~1/60s

            if (pjTimer >= pjCooldown) {
                shoot();
                pjTimer = 0;
            }

            // Atualiza e remove projeteis
            for (int i = 0; i < projCont.size(); i++) {
                Projetil novoProjetil = projCont.get(i);

                novoProjetil.update();

                if (novoProjetil.isOutOfBounds(getWidth(), getHeight())) {
                    projCont.remove(i);
                    i--;
                }
            }

        }

        private java.util.List<Projetil> projCont = new ArrayList<>();

        private void shoot() {
            double dirX = mouseX - (playerX + pWidth / 2);
            double dirY = mouseY - (playerY + pHeight / 2);

            Projetil novoProjetil = new Projetil(
                playerX + pWidth / 2,
                playerY + pHeight / 2,
                dirX,
                dirY,
                8
            );

            projCont.add(novoProjetil);
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

            // Projeteis
            for (Projetil p : projCont) {
            p.draw(g);
            }
        }

    }
}
