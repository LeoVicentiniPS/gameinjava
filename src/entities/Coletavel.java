package entities;

import java.awt.Graphics;

public abstract class Coletavel extends Entidade {
    protected boolean coletado = false;

    public Coletavel(int x, int y, int size) {
        super(x, y, size);
        
    }

    public void foiColetado() {
        coletado = true;
    }

    public boolean checkColetado() {
        return coletado;
    }

    public void checarColeta(Player player) {
        if (this.colide(player)) {
            aplicarEfeito(player);
            this.foiColetado();
        }
    }

    protected abstract void aplicarEfeito(Player player);

    @Override
    public abstract void update();

    @Override
    public abstract void draw(Graphics g);
}
