package entities;
import java.awt.Graphics;

public abstract class Entidade {
    protected int x, y;
    protected double dx = 0, dy = 0;
    protected double speed = 0;

    protected Entidade(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void normalizarMovimento() {
        double comprimento = Math.sqrt(dx * dx + dy * dy);
        if (comprimento != 0) {
            dx /= comprimento;
            dy /= comprimento;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public int getNextX() {
        return (int) (x + dx * speed);
    }

    public int getNextY() {
        return (int) (y + dy * speed);
    }

    public abstract void update();
    public abstract void draw(Graphics g);
}
