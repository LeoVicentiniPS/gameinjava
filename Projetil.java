import java.awt.*;
import static utils.Config.*;

public class Projetil {
    double x, y;
    double dx, dy;
    double speed;

    // Inicia projétil com coordenada, direção inicial e velocidade
    public Projetil(double startX, double startY, double dirX, double dirY, double speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        // Normalização do vetor
        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        if (length != 0) {
            this.dx = dirX / length;
            this.dy = dirY / length;
        }
    }

    public void update() {
        x += dx * speed;
        y += dy * speed;
    }

    public void draw(Graphics g) {
        g.setColor(PROJECTILE_COLOR);
        g.fillOval((int) x, (int) y, PROJECTILE_SIZE, PROJECTILE_SIZE);
    }

    public boolean isOutOfBounds(int width, int height) {
        return x < 0 || x > width || y < 0 || y > height;
    }
}
