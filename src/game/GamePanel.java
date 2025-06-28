
package game;
import entities.Inimigo;
import entities.Player;
import entities.Projetil;
import entities.XpOrb;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import resources.sounds.Sound;
import static ui.Interface.*;
import static utils.Config.*;
import utils.Config.EstadoJogo;


public class GamePanel extends JPanel implements Runnable {

    private Thread gameThread;
    private boolean isRunning;
    private EstadoJogo estadoAtual = EstadoJogo.MENU;

    private double pjCooldown = PROJECTILE_COOLDOWN;
    private double pjTimer = 0;

    private double inCooldown = ENEMY_COOLDOWN;
    private double inTimer = 0;

    private List<Projetil> projList = new ArrayList<>();
    private List<Inimigo> enemyList = new ArrayList<>();
    private List<XpOrb> xpList = new ArrayList<>();


    private Player player;

    private int mouseX = WINDOW_WIDTH / 2;
    private int mouseY = WINDOW_HEIGHT / 2;

    private void resetGame() {
        // Limpa tudo
        projList.clear();
        enemyList.clear();
        xpList.clear();
        player.resetXp();

        player.setX(PLAYER_START_X);
        player.setY(PLAYER_START_Y);

        pjTimer = 0;
        inTimer = 0;
    }

    private Point gerarSpawnAleatorio() {
        int margem = 50; 
        int lado = (int)(Math.random() * 4); 

        int x = 0, y = 0;

        switch (lado) {
            case 0: 
                x = (int)(Math.random() * WINDOW_WIDTH);
                y = -margem;
                break;
            case 1: 
                x = (int)(Math.random() * WINDOW_WIDTH);
                y = WINDOW_HEIGHT + margem;
                break;
            case 2: 
                x = -margem;
                y = (int)(Math.random() * WINDOW_HEIGHT);
                break;
            case 3: 
                x = WINDOW_WIDTH + margem;
                y = (int)(Math.random() * WINDOW_HEIGHT);
                break;
        }

        return new Point(x, y);
    }

    public GamePanel() {

        setFocusable(true);
        SwingUtilities.invokeLater(this::requestFocusInWindow);
        requestFocusInWindow();

        player = new Player(PLAYER_START_X, PLAYER_START_Y, PLAYER_WIDTH, PLAYER_HEIGHT);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                //Controle de estados
                if (estadoAtual == EstadoJogo.JOGANDO){

                    player.adicionarTecla(e.getKeyCode());

                    if(e.getKeyCode() == KeyEvent.VK_P) {
                        estadoAtual = EstadoJogo.PAUSADO;
                    }
                } 
                else if (estadoAtual == EstadoJogo.MENU && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    estadoAtual = EstadoJogo.JOGANDO;
                    resetGame();
                } 
                else if (estadoAtual == EstadoJogo.PAUSADO && e.getKeyCode() == KeyEvent.VK_P) {
                    estadoAtual = EstadoJogo.JOGANDO;
                } 
                else if (estadoAtual == EstadoJogo.GAME_OVER && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    estadoAtual = EstadoJogo.JOGANDO;
                    resetGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                    player.removerTecla(e.getKeyCode());
                
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                player.setMousePosition(mouseX, mouseY);
            }
        });

        startGame();
    }

    public void startGame() {

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

        if(estadoAtual == EstadoJogo.JOGANDO){
            pjTimer += 1.0 / 60.0;
            inTimer += 1.0 / 60.0;

            player.update();

            if (pjTimer >= pjCooldown) {
                shoot();
                pjTimer = 0;
            }

            // Atualiza e remove projeteis
            for (int i = 0; i < projList.size(); i++) {
                Projetil novoProjetil = projList.get(i);

                novoProjetil.update();

                if (novoProjetil.isOutOfBounds(getWidth(), getHeight())) {
                    projList.remove(i);
                    i--;
                }
            }

            for(Inimigo inimigo : enemyList) {
                inimigo.update();
            }

            // Evita que inimigos se sobreponham
            for (int i = 0; i < enemyList.size(); i++) {
                Inimigo a = enemyList.get(i);

                for (int j = i + 1; j < enemyList.size(); j++) {
                    Inimigo b = enemyList.get(j);

                    if (a.colide(b)) {
                    // Calcula vetor de afastamento
                        int dx = a.getX() - b.getX();
                        int dy = a.getY() - b.getY();

                        // Evita divisão por zero
                        if (dx == 0 && dy == 0) {
                            dx = 1;
                            dy = 1;
                        }

                        double dist = Math.sqrt(dx * dx + dy * dy);
                        double push = 1.0; // força para separação

                        // Normaliza vetor e aplica separação
                        a.setX((int)(a.getX() + push * (dx / dist)));
                        a.setY((int)(a.getY() + push * (dy / dist)));

                        b.setX((int)(b.getX() - push * (dx / dist)));
                        b.setY((int)(b.getY() - push * (dy / dist)));
                    }
                }
            }

            // Spawn aleatório de inimigos
            if (inTimer >= inCooldown) {
                Point posRandom = gerarSpawnAleatorio();
                enemyList.add(new Inimigo(posRandom.x,posRandom.y, 30, 30, player));
                inTimer = 0;
            }

            // Verifica colisões entre projéteis e inimigos
            for (int i = 0; i < projList.size(); i++) {
                Projetil proj = projList.get(i);

                for (int j = 0; j < enemyList.size(); j++) {
                    Inimigo inimigo = enemyList.get(j);

                    if (proj.colide(inimigo)) {
                        projList.remove(i);
                        enemyList.remove(j);
                        i--;
                        xpList.add(new XpOrb(inimigo.getX(), inimigo.getY(), 10, player));
                        break; 
                    }
                }
            }

            for (XpOrb exp : xpList) {
                exp.update();
                if (exp.colide(player)) {
                    Sound.play("exp.wav");
                    player.ganharXp(exp.getXpValue()); 
                    exp.foiColetado();
                }
            }

            xpList.removeIf(orb -> orb.checkColetado());

            // Verifica colisão entre inimigo e jogador
            for (Inimigo inimigo : enemyList) {
                if (inimigo.colide(player)) {
                    estadoAtual = EstadoJogo.GAME_OVER;
                    return;
                }
            }
        }
    }

    private void shoot() {
        int centerX = player.getX() + PLAYER_WIDTH / 2;
        int centerY = player.getY() + PLAYER_HEIGHT / 2;

        double dirX = mouseX - centerX;
        double dirY = mouseY - centerY;

        Projetil novoProjetil = new Projetil(
            centerX,
            centerY,
            dirX,
            dirY,
            PROJECTILE_SPEED
        );

        projList.add(novoProjetil);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (estadoAtual == EstadoJogo.JOGANDO) {
            player.draw(g);
            for (Projetil p : projList) p.draw(g);
            for (Inimigo i : enemyList) i.draw(g);
            for (XpOrb orb : xpList) orb.draw(g);

        } 
        else if (estadoAtual == EstadoJogo.MENU) {
            drawMenu(g);
        } 
        else if (estadoAtual == EstadoJogo.PAUSADO) {
            player.draw(g);
            for (Projetil p : projList) p.draw(g);
            for (Inimigo i : enemyList) i.draw(g);
            drawPause(g);
        } 
        else if (estadoAtual == EstadoJogo.GAME_OVER) {
            drawGameOver(g);
        }
    }
}