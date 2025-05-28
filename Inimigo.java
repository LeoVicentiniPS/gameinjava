
import java.awt.*;
public class Inimigo extends Entidade {
    private final int width = 40;
    private final int height = 40;
    private final Color color = Color.BLACK;

    private Player alvo;

    public Inimigo(int x, int y, Player alvo) {
        super(x, y);     
        this.speed = 2.0;    
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
