package entities;

import java.awt.Graphics;

public abstract class Entidade {
    protected int x, y;
    protected int width, height;
    protected double dx = 0, dy = 0;
    protected double speed = 0;

    protected Entidade(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected Entidade(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
    }

    protected void normalizarMovimento() {
        double comprimento = Math.sqrt(dx * dx + dy * dy);
        if (comprimento != 0) {
            dx /= comprimento;
            dy /= comprimento;
        }
    }

    public boolean colide(Entidade other) {
        return x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
