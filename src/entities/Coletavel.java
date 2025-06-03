package entities;

import java.awt.Graphics;

public abstract class Coletavel extends Entidade {
    protected boolean coletado = false;

    public Coletavel(int x, int y, int width, int height) {
        super(x, y, width, height);
        
    }

    public void foiColetado() {
        coletado = true;
    }

    public boolean checkColetado() {
        return coletado;
    }

    public abstract void checarColeta(Player player);

    @Override
    public abstract void update();

    @Override
    public abstract void draw(Graphics g);
}
