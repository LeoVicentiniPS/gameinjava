package entities;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;
import static utils.Config.*;

public class Player extends Entidade {

    private final int width;
    private final int height;

    private int mouseX = WINDOW_WIDTH / 2;
    private int mouseY = WINDOW_HEIGHT / 2;

    private final Set<Integer> teclasPressionadas = new HashSet<>();

    public Player(int x, int y, int width, int height) {
        super(x, y);
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.speed = PLAYER_SPEED;
    }

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

    @Override
    public void update() {
        dx = 0;
        dy = 0;

        // Direcionais ou WASD
        if (teclasPressionadas.contains(KeyEvent.VK_LEFT) || teclasPressionadas.contains(KeyEvent.VK_A)) {
            dx -= 1;
        }
        if (teclasPressionadas.contains(KeyEvent.VK_RIGHT) || teclasPressionadas.contains(KeyEvent.VK_D)) {
            dx += 1;
        }
        if (teclasPressionadas.contains(KeyEvent.VK_UP) || teclasPressionadas.contains(KeyEvent.VK_W)) {
            dy -= 1;
        }
        if (teclasPressionadas.contains(KeyEvent.VK_DOWN) || teclasPressionadas.contains(KeyEvent.VK_S)) {
            dy += 1;
        }

        normalizarMovimento();
        int nextX = getNextX();
        int nextY = getNextY();

        // Limites da tela
        if (nextX >= 0 && nextX + width <= WINDOW_WIDTH) {
            x = nextX;
        }
        if (nextY >= 0 && nextY + height <= WINDOW_HEIGHT) {
            y = nextY;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}
