package entities;
import java.awt.*;
import static utils.Config.*;

public class Inimigo extends Entidade {
    private final int width = ENEMY_WIDTH;
    private final int height = ENEMY_HEIGHT;
    private final Color color = Color.BLACK;

    private Player alvo;

    public Inimigo(int x, int y, Player alvo) {
        super(x, y);     
        this.speed = ENEMY_SPEED;    
        this.alvo = alvo;
    }

    @Override
    public void update() {
        dx = alvo.getX() - x;
        dy = alvo.getY() - y;
        normalizarMovimento();

        x += dx * speed;
        y += dy * speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
