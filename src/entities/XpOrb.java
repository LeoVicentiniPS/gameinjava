package entities;

import java.awt.Color;
import java.awt.Graphics;
import resources.sounds.Sound;
import static utils.Config.*;

public class XpOrb extends Coletavel {
    private final int xpValue = 1; 
    private Player target;
    protected int size;
    private int nearDistance = 200;
    private double pulseTime = 0;
    private float hue = 0;
    private double flutuate = 0;
    private boolean near = false;

    public XpOrb(int x, int y, int size, Player target) {
        super(x, y, size);
        this.speed = ORB_SPEED; 
        this.size = size;
        this.target = target;
    }

    @Override
    protected void aplicarEfeito(Player player) {
        player.ganharXp(xpValue);
        Sound.play("exp.wav");
    }

    @Override
    public void update() {

        hue += 0.01f;
        if (hue > 1){
            hue = 0;
        }

        flutuate += 0.1;
        pulseTime += 0.1;

        dx = target.getX() - x;
        dy = target.getY() - y;

        double closeEnough = Math.hypot(dx, dy);

        if (closeEnough < nearDistance) 
            near = true;

        if (near){
            normalizarMovimento();
            x += dx * speed;
            y += dy * speed;
            speed = speed + 0.15;
        }
    }

    @Override
    public void draw(Graphics g) {
        int pulse = (int)(2 * Math.sin(pulseTime)) + size;
        int flut = (int)(3 * Math.sin(flutuate));
        g.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));
        g.fillOval(x - pulse / 2, (y - pulse / 2) - flut, pulse, pulse);
    }

    public int getXpValue() {
        return xpValue;
    }

}
