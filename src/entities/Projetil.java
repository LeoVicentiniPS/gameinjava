package entities;
import java.awt.*;
import static utils.Config.*;

public class Projetil extends Entidade {
    private final Color color = Color.RED;
    private int size = PROJECTILE_SIZE;

    public Projetil(int x, int y, double dirX, double dirY, double speed) {
        super(x, y, PROJECTILE_SIZE);
        this.dx = dirX;
        this.dy = dirY;
        this.speed = speed;
        normalizarMovimento();
    }

    @Override
    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }

    public boolean isOutOfBounds(int largura, int altura) {
        return x < 0 || y < 0 || x > largura || y > altura;
    }
}
