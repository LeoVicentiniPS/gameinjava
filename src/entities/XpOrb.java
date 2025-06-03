package entities;

import java.awt.Color;
import java.awt.Graphics;
import static utils.Config.*;

public class XpOrb extends Coletavel {
    private final int xpValue = 1; 
    private Player target;
    protected int size;

    public XpOrb(int x, int y, int size, Player target) {
        super(x, y, size, size);
        this.speed = ORB_SPEED; 
        this.size = size;
        this.target = target;
    }

    @Override
    public void update() {
        dx = target.getX() - x;
        dy = target.getY() - y;
        normalizarMovimento();

        x += dx * speed;
        y += dy * speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size, size);
    }

    public int getXpValue() {
        return xpValue;
    }
    
    @Override
    public void checarColeta(Player a){
        this.foiColetado();
    }
}
