package entities;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import static utils.Config.*;

public class Player extends Entidade {

    private final Set<Integer> teclasPressionadas = new HashSet<>();

    private int mouseX = WINDOW_WIDTH / 2;
    private int mouseY = WINDOW_HEIGHT / 2;

    private int experience = 0;
    private int nivel = 1;
    private int xpParaProximoNivel = 10;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.speed = PLAYER_SPEED;
    }

    // ===== CONTROLES =====
    public void setMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void adicionarTecla(int keyCode) {
        teclasPressionadas.add(keyCode);
    }

    public void removerTecla(int keyCode) {
        teclasPressionadas.remove(keyCode);
    }

    public Set<Integer> getTeclasPressionadas() {
        return teclasPressionadas;
    }

    // ===== SISTEMA DE XP =====
    public void ganharXp(int quantidade) {
        experience += quantidade;

        while (experience >= xpParaProximoNivel) {
            experience -= xpParaProximoNivel;
            nivel++;
            xpParaProximoNivel += 5; 

            onLevelUp(); 
        }
    }

    private void onLevelUp() {
        System.out.println("Subiu para o nível " + nivel + "!");
        // O que fazer em lvl up
    }

    public void resetXp() {
        experience = 0;
        nivel = 1;
        xpParaProximoNivel = 10;
    }

    public int getNivel() {
        return nivel;
    }

    public int getXp() {
        return experience;
    }

    public int getXpParaProximoNivel() {
        return xpParaProximoNivel;
    }

    // ===== UPDATE MOVIMENTO =====
    @Override
    public void update() {
        dx = 0;
        dy = 0;

        if (teclasPressionadas.contains(KeyEvent.VK_LEFT) || teclasPressionadas.contains(KeyEvent.VK_A)) dx -= 1;
        if (teclasPressionadas.contains(KeyEvent.VK_RIGHT) || teclasPressionadas.contains(KeyEvent.VK_D)) dx += 1;
        if (teclasPressionadas.contains(KeyEvent.VK_UP) || teclasPressionadas.contains(KeyEvent.VK_W)) dy -= 1;
        if (teclasPressionadas.contains(KeyEvent.VK_DOWN) || teclasPressionadas.contains(KeyEvent.VK_S)) dy += 1;

        normalizarMovimento();

        int nextX = getNextX();
        int nextY = getNextY();

        if (nextX >= 0 && nextX + width <= WINDOW_WIDTH) x = nextX;
        if (nextY >= 0 && nextY + height <= WINDOW_HEIGHT) y = nextY;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);

        // Info de XP 
        g.setColor(Color.BLACK);
        g.drawString("XP: " + experience + "/" + xpParaProximoNivel, x, y - 10);
        g.drawString("Lv " + nivel, x, y - 25);
    }
}
