import java.util.List;
import java.util.ArrayList;

import static utils.Config.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Set;
import java.util.HashSet;

public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;
    private boolean isRunning;

    private int mouseX = WINDOW_WIDTH / 2;
    private int mouseY = WINDOW_HEIGHT / 2;
    private int playerX, playerY;
    private int pWidth = PLAYER_WIDTH;
    private int pHeight = PLAYER_HEIGHT;
    private double pjCooldown = PROJECTILE_COOLDOWN;
    private double pjTimer = 0;

    private Set<Integer> teclasPressionadas = new HashSet<>();
    private List<Projetil> projCont = new ArrayList<>();

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
        playerX = PLAYER_START_X;
        playerY = PLAYER_START_Y;

        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
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
        int nextX = (int) (playerX + dx * PLAYER_SPEED);
        int nextY = (int) (playerY + dy * PLAYER_SPEED);

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

    private void shoot() {
        double dirX = mouseX - (playerX + pWidth / 2);
        double dirY = mouseY - (playerY + pHeight / 2);

        Projetil novoProjetil = new Projetil(
                playerX + pWidth / 2,
                playerY + pHeight / 2,
                dirX,
                dirY,
                PROJECTILE_SPEED);

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